package com.example.yuanren123.jinchuan.activity.strengthen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.adapter.strengthen.StrengthenAdapter;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.OneWordsBean;
import com.example.yuanren123.jinchuan.model.PhraseBean;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by yuanren123 on 2017/9/14.
 */
@ContentView(R.layout.activity_strength)
public class StrengthenActivity extends BaseActivity {

      private List<OneWordsBean> dataBase;
      private List<OneWordsBean>  datas;
      @ViewInject(R.id.rv_review)
      private RecyclerView recyclerView;
      @ViewInject(R.id.tv_words_show)
      private TextView tv_words_show;

      private StrengthenAdapter adapter;
      private int number;
      private   List<ReviewBean> data;
      @ViewInject(R.id.tv_strength_num)
      private TextView tv_num;
      @ViewInject(R.id.rl_words_home)
      private RelativeLayout rl_home;

      private LocalBroadcastManager broadcastManager;
      private IntentFilter intentFilter;
      private BroadcastReceiver mReceiver;


      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            number = 0;
            SharedPreferences sp = getSharedPreferences("StudyPlan", Context.MODE_PRIVATE);
            int numberX = sp.getInt("wordsNum",8);
            int Progress = SharedPreferencesUtils.getStudyProgressTwo(StrengthenActivity.this);
            int num =  Progress-numberX+1;
            dataBase = new ArrayList<>();
            for (int i = 0; i <numberX ; i++) {
                  List<PhraseBean> PhraseBeans = DataSupport.where("Tag = ?", num+i+"").find(PhraseBean.class);
                  OneWordsBean dataBase2 = new OneWordsBean();
                  try{
                        dataBase2.setJa(PhraseBeans.get(0).getJa());
                        dataBase2.setCx(PhraseBeans.get(0).getCx());
                        dataBase2.setCh(PhraseBeans.get(0).getCh());
                        dataBase2.setPic(PhraseBeans.get(0).getPic());
                        dataBase2.setJe(PhraseBeans.get(0).getJe());
                        dataBase2.setCe(PhraseBeans.get(0).getCe());
                        dataBase2.setJaudio(PhraseBeans.get(0).getJaudio());
                        dataBase2.setJsentence(PhraseBeans.get(0).getJsentence());
                        dataBase2.setJvideo(PhraseBeans.get(0).getJvideo());
                        dataBase2.setId(PhraseBeans.get(0).getId());
                        dataBase2.setTag(PhraseBeans.get(0).getTag());
                        dataBase.add(dataBase2);
                  }catch (Exception e){

                  }
            }
            Log.d("dwadsds", dataBase.size()+"");
            data = new ArrayList<ReviewBean>();
            data = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
            if (data.size() == 0 || data == null){
                  finish();
            }else {
                  tv_num.setText("需复习:"+data.size());
            }
            Collections.shuffle(data);
            datas = random(dataBase,dataBase.size()-1,data.get(0).getNowNuM());
            Collections.shuffle(datas);
            tv_words_show.setText(data.get(number).getJa());
            adapter = new StrengthenAdapter(StrengthenActivity.this,datas,data.get(number).getJa(),handler);
            recyclerView.setLayoutManager(new LinearLayoutManager(StrengthenActivity.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
            broadcastManager = LocalBroadcastManager.getInstance(this);
            intentFilter = new IntentFilter();
            intentFilter.addAction("chooseRight");
            mReceiver = new BroadcastReceiver() {
                  @Override
                  public void onReceive(Context context, Intent intent) {
                        Log.d("dwadwd", "走了");
                        data = new ArrayList<ReviewBean>();
                        data = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                        if (data.size() == 0 || data == null){
                              finish();
                        }else {
                              tv_num.setText("需复习:"+data.size());
                        }
                        Collections.shuffle(data);

                        datas = random(dataBase,dataBase.size()-1,data.get(0).getNowNuM());
                        Collections.shuffle(datas);
                        tv_words_show.setText(data.get(number).getJa());
                        adapter = new StrengthenAdapter(StrengthenActivity.this,datas,data.get(number).getJa(),handler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(StrengthenActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);

                  }
            };
            broadcastManager.registerReceiver(mReceiver, intentFilter);

            rl_home.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        finish();
                  }
            });
      }


      Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                  if (msg.what == 0){
                        Intent intent = new Intent(StrengthenActivity.this,StrengthenDetailActivity.class);
                        intent.putExtra("number",1);
                        intent.putExtra("Ja",data.get(number).getJa());
                        intent.putExtra("Cx",data.get(number).getCx());
                        intent.putExtra("Pic",data.get(number).getPic());
                        intent.putExtra("Je",data.get(number).getJe());
                        intent.putExtra("Ce",data.get(number).getCe());
                        intent.putExtra("Ch",data.get(number).getCh());
                        intent.putExtra("Jaudio",data.get(number).getJaudio());
                        intent.putExtra("Id",data.get(number).getId());
                        intent.putExtra("Jsentence",data.get(number).getJsentence());
                        intent.putExtra("Jvideo",data.get(number).getJvideo());
                        intent.putExtra("Tag",data.get(number).getTag());
                        startActivity(intent);
                  }
                  else if(msg.what ==1){
                        Intent intent = new Intent(StrengthenActivity.this,StrengthenDetailActivity.class);
                        intent.putExtra("number",0);
                        intent.putExtra("Ja",data.get(number).getJa());
                        intent.putExtra("Cx",data.get(number).getCx());
                        intent.putExtra("Pic",data.get(number).getPic());
                        intent.putExtra("Je",data.get(number).getJe());
                        intent.putExtra("Ce",data.get(number).getCe());
                        intent.putExtra("Ch",data.get(number).getCh());
                        intent.putExtra("Jaudio",data.get(number).getJaudio());
                        intent.putExtra("Id",data.get(number).getId());
                        intent.putExtra("Jsentence",data.get(number).getJsentence());
                        intent.putExtra("Jvideo",data.get(number).getJvideo());
                        intent.putExtra("Tag",data.get(number).getTag());
                        startActivity(intent);
                  }else  if (msg.what ==2){
                        Intent intent = new Intent(StrengthenActivity.this,StrengthenDetailActivity.class);
                        intent.putExtra("number",-1);
                        intent.putExtra("Ja",data.get(number).getJa());
                        intent.putExtra("Cx",data.get(number).getCx());
                        intent.putExtra("Pic",data.get(number).getPic());
                        intent.putExtra("Je",data.get(number).getJe());
                        intent.putExtra("Ce",data.get(number).getCe());
                        intent.putExtra("Ch",data.get(number).getCh());
                        intent.putExtra("Jaudio",data.get(number).getJaudio());
                        intent.putExtra("Id",data.get(number).getId());
                        intent.putExtra("Jsentence",data.get(number).getJsentence());
                        intent.putExtra("Jvideo",data.get(number).getJvideo());
                        intent.putExtra("Tag",data.get(number).getTag());
                        startActivity(intent);
                  }


                  super.handleMessage(msg);
            }
      };



      public static  List<OneWordsBean> random( List<OneWordsBean> dataBase, int maxValue,int NowNum){



            List<Integer> list = new ArrayList<Integer>();
            Random r = new Random();

            while(list.size()<3)

            {
                  int num = r.nextInt(maxValue);
                  Log.d("sdadawdwa", "运行1"+ num);
                  if (!list.contains(num)&&num !=NowNum) {
                        Log.d("sdadawdwa", num+"!");
                        list.add(num);
                  }
            }


            List<OneWordsBean> result = new ArrayList<>();

            for(int i=0;i<3;i++){
                  result.add(dataBase.get(list.get(i)));
            }

            result.add(dataBase.get(NowNum));
            return result;
      }


      @Override
      public void initView() {

      }

      @Override
      public void initData() {

      }

      @Override
      public int setLayout() {
            return R.layout.activity_words;
      }
}
