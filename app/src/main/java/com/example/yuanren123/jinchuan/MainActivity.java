package com.example.yuanren123.jinchuan;



import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.fragment.OneFragment;
import com.example.yuanren123.jinchuan.fragment.ThreeFragment;
import com.example.yuanren123.jinchuan.fragment.TwoFragment;
import com.example.yuanren123.jinchuan.model.DateBean;
import com.example.yuanren123.jinchuan.model.PhraseBean;
import com.example.yuanren123.jinchuan.model.ResultBean;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.model.WordsBean;
import com.example.yuanren123.jinchuan.service.VideoService;
import com.example.yuanren123.jinchuan.until.HttpDownloader;
import com.example.yuanren123.jinchuan.until.NetUtil;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import com.example.yuanren123.jinchuan.view.MyRadioGroup;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {


    private boolean adIsFinish = false;
    private int delayTime = 3;// 广告4秒倒计时
    private int showFragment = -1;
    @ViewInject(R.id.main_rg)
    private MyRadioGroup rg;
    @ViewInject(R.id.rbt_one)
    private RadioButton radioButton;
    @ViewInject(R.id.rbt_two)
    private RadioButton radioButton2;
    private List<Fragment> fragmentList = new ArrayList<>();
    private WordsBean data;
    private ResultBean ResultData;
    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver mReceiver;
    private IntentFilter intentFilter1;
    private BroadcastReceiver mReceiver1;


    @ViewInject(R.id.rl_show_ad)
    private RelativeLayout rl;
    private   AlphaAnimation disappearAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        disappearAnimation = new AlphaAnimation(1, 0);
        disappearAnimation.setDuration(500);
        adIsFinish = true;
        handler1.sendEmptyMessageDelayed(1, 1000);

          String url = "http://www.ibianma.com/video/getVideoList.php?type=2&timer="+0+"&dir=pull&num=100";
          if(NetUtil.isNetworkAvailable(this)) {

              Log.d("32131231assda", "开始请求::"+url);
                OkHttpClient mOkHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                          .url(url)
                          .build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                      @Override
                      public void onFailure(Call call, IOException e) {
                            Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                            finish();
                      }
                      @Override
                      public void onResponse(Call call, Response response) throws IOException {
                            Message message = Message.obtain();
                            message.what = 1;
                            Bundle bundleData = new Bundle();
                            bundleData.putString("Result", response.body().string());
                            message.setData(bundleData);
                            handler.sendMessage(message);
                      }

                });
          }else {
                Toast.makeText(this, "当前网络不可用", Toast.LENGTH_LONG).show();
                finish();
          }

        OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        ThreeFragment threeFragment = new ThreeFragment();
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        replace(0);

        List<ReviewBean> ReviewData = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
        if (ReviewData.size() == 0 || ReviewData == null){

        }else {
            for (int i = 0; i <ReviewData.size() ; i++) {

                ContentValues values = new ContentValues();
                values.put("wrongNum", "0");
                DataSupport.updateAll(ReviewBean.class, values, "ja= ?", ReviewData.get(i).getJa());
            }
        }

        Calendar calendarToday = Calendar.getInstance();
        int month = calendarToday.get(Calendar.MONTH)+1;
        int  day = calendarToday.get(Calendar.DAY_OF_MONTH);
        int year = calendarToday.get(Calendar.YEAR);
        List<DateBean>  dateBeans = DataSupport.where("year = ?", year+"").where("month = ?", month+"").where("day = ?", day+"").find(DateBean.class);
        if (dateBeans.size() == 0){
            Connector.getDatabase();
            DateBean dateBean = new DateBean();
            dateBean.setYear(year);
            dateBean.setMonth(month);
            dateBean.setDay(day);
            dateBean.save();
        }else {

        }

        //
        //                                                                                                                                rg = (MyRadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
                                          @Override
                                          public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                                              switch (checkedId) {
                                                  case R.id.rbt_one:
                                                      replace(0);

                                                      break;
                                                  case R.id.rbt_two:
                                                      replace(1);

                                                      break;
                                                  case R.id.rbt_three:

                                                      replace(2);
                                                      break;
                                              }
                                          }
                                      }

        );





        broadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("plan");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                radioButton.setChecked(true);
                replace(0);

                }


        };
        broadcastManager.registerReceiver(mReceiver, intentFilter);



        intentFilter1 = new IntentFilter();
        intentFilter1.addAction("goReview");
        mReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

               radioButton2.setChecked(true);
       //         replace(1);

            }


        };
        broadcastManager.registerReceiver(mReceiver1, intentFilter1);


    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1){
                Bundle result = msg.getData();
                String data1 = result.getString("Result");
                Gson gson = new Gson();
                ResultData = gson.fromJson( data1
                          , ResultBean.class);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                int num = ResultData.getRv().size();
                                for (int i = 0; i <num ; i++) {
                                    String url = "http://"+ResultData.getRv().get(i).getSummary();
                                    Log.d("xindepeifang", "handleMessage: "+url);

                                    if(NetUtil.isNetworkAvailable(MainActivity.this)) {

                                        OkHttpClient mOkHttpClient = new OkHttpClient();
                                        final Request request = new Request.Builder()
                                                  .url(url)
                                                  .build();
                                        Call call = mOkHttpClient.newCall(request);
                                        call.enqueue(new okhttp3.Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                                                finish();
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                Message message = Message.obtain();
                                                message.what = 2;
                                                Bundle bundleData = new Bundle();
                                                bundleData.putString("Result", response.body().string());
                                                message.setData(bundleData);
                                                handler.sendMessage(message);

                                            }

                                        });
                                    }else {
                                        Toast.makeText(MainActivity.this, "当前网络不可用", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            }catch (Exception e){

                            }
                        }
                    });
                thread.start();


            }else if (msg.what ==2){

                Bundle result = msg.getData();
                String data1 = result.getString("Result");
                Gson gson = new Gson();
                    data = gson.fromJson( data1
                              , WordsBean.class);
                    for (int i = 0; i <data.getList().size() ; i++) {
                        PhraseBean wordsBean = new PhraseBean();
                        List<PhraseBean> PhraseBeans = DataSupport.where("Tag = ?", data.getList().get(i).getTag()).find(PhraseBean.class);
                        if (PhraseBeans.size()!= 0){
                            Log.d("xindepeifang", "跳出循环"+data.getList().get(i).getTag()+data.getList().get(i).getJa());

                            break;
                        }else {
                            Log.d("xindepeifang", "保存了"+data.getList().get(i).getTag());
                            wordsBean.setTag(Integer.parseInt(data.getList().get(i).getTag()));
                            wordsBean.setJa(data.getList().get(i).getJa());
                            wordsBean.setCx(data.getList().get(i).getCx());
                            wordsBean.setCh(data.getList().get(i).getCh());
                            wordsBean.setPic(data.getList().get(i).getPic());
                            wordsBean.setJe(data.getList().get(i).getJe());
                            wordsBean.setCe(data.getList().get(i).getCe());
                            wordsBean.setJaudio(data.getList().get(i).getJaudio());
                            wordsBean.setJsentence(data.getList().get(i).getJsentence());
                            wordsBean.setJvideo(data.getList().get(i).getJvideo());
                            wordsBean.setType(Integer.parseInt(data.getList().get(i).getType()));
                            wordsBean.setShowType(0);
                            wordsBean.save();
                        }
                    }
                new downloadMP3Thread().start();
                new downloadJMP3Thread().start();
                Log.d("32131231assda", "开始请求"+data.getName());
                SharedPreferencesUtils.SaveTimer(MainActivity.this,ResultData.getRv().get(ResultData.getRv().size()-1).getTimer()+"");
            }else if(msg.what == 9){
                Toast.makeText(MainActivity.this, "当前网络不可用", Toast.LENGTH_LONG).show();
                finish();
            }
            super.handleMessage(msg);
        }
    };




    @Override
    public void initView() {


    }

    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (adIsFinish) {
                        if (delayTime > 0) {
                            rl.setVisibility(View.VISIBLE);
                            handler1.sendEmptyMessageDelayed(1, 1000);
                            delayTime--;
                        } else {

                            rl.startAnimation(disappearAnimation);
                            disappearAnimation.setAnimationListener(new Animation.AnimationListener() {

                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationRepeat(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    rl.setVisibility(View.GONE);
                                }
                            });

                            handler1.removeCallbacksAndMessages(null);
                            handler1 = null;
                        }
                    } else {

                        rl.setVisibility(View.GONE);
                    }
                    break;

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, VideoService.class);
        stopService(intent);
    }

    public void replace(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (showFragment >= 0) {
            transaction.hide(fragmentList.get(showFragment));
            if (!fragmentList.get(i).isAdded()) {
                transaction.add(R.id.replace, fragmentList.get(i));

            }
        } else {
            transaction.add(R.id.replace, fragmentList.get(i));

        }
        transaction.show(fragmentList.get(i));
        transaction.commitAllowingStateLoss();
        showFragment = i;

    }
    class downloadMP3Thread extends Thread{
        public void run(){
//            List<PhraseBean> PhraseBeans = DataSupport.findAll(PhraseBean.class);
            for (int i = 0; i <data.getList().size() ; i++) {
                HttpDownloader httpDownloader = new HttpDownloader();
                int downloadResult = httpDownloader.downloadFiles(data.getList().get(i).getJaudio()
                          ,"jinchuanxiaociMp3",data.getList().get(i).getTag()+".mp3");
                Log.d("3213sdasdad", "下载结果："+downloadResult+ Environment.getExternalStorageDirectory());

            }

        }
    }
    class downloadJMP3Thread extends Thread{
        public void run(){
//            List<PhraseBean> PhraseBeans = DataSupport.findAll(PhraseBean.class);
            for (int i = 0; i <data.getList().size() ; i++) {
                HttpDownloader httpDownloader = new HttpDownloader();
                int downloadResult = httpDownloader.downloadFiles(data.getList().get(i).getJsentence()
                          ,"jinchuanxiaociMp3","J"+data.getList().get(i).getTag()+".mp3");
                Log.d("3213sdasdad", "下载结果："+downloadResult+ Environment.getExternalStorageDirectory());

            }

        }
    }




    @Override
    public void initData() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }
}
