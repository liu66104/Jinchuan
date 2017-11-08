package com.example.yuanren123.jinchuan.activity.card;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.adapter.card.CardAdapter;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.ResultBean;
import com.example.yuanren123.jinchuan.until.NetUtil;
import com.example.yuanren123.jinchuan.view.xlistview.XListView;
import com.google.gson.Gson;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuanren123 on 2017/10/27.
 */

@ContentView(R.layout.activity_card)
public class CardActivity extends BaseActivity implements XListView.IXListViewListener {

      @ViewInject(R.id.card_iv_back)
      private ImageView iv_back;
      @ViewInject(R.id.xlv_card)
      private XListView xListView;
      private CardAdapter adapter;
      private ResultBean ResultData;
      private Handler mHandler;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            xListView.setPullLoadEnable(true);
            xListView.setXListViewListener(this);
            mHandler = new Handler();
            iv_back.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        finish();
                  }
            });


            String url = "http://www.ibianma.com/video/getVideoList.php?type=2&timer=0&dir=pull&num=8";
            if(NetUtil.isNetworkAvailable(this)) {
                  OkHttpClient mOkHttpClient = new OkHttpClient();
                  final Request request = new Request.Builder()
                            .url(url)
                            .build();
                  Call call = mOkHttpClient.newCall(request);
                  call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                              Toast.makeText(CardActivity.this, "网络异常", Toast.LENGTH_LONG).show();
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

//            xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                  @Override
//                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent  = new Intent(CardActivity.this, CardShowActivity.class);
//                        intent.putExtra("url","http://"+ResultData.getRv().get(i).getSummary());
//                        startActivity(intent);
//                  }
//            });
}
      Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                  if (msg.what ==1){
                        Bundle result = msg.getData();
                        String data = result.getString("Result");
                        Gson gson = new Gson();
                        ResultData = gson.fromJson( data
                                  , ResultBean.class);
                        adapter = new CardAdapter(CardActivity.this,ResultData.getRv());
                        xListView.setAdapter(adapter);
                        super.handleMessage(msg);
                  }else if (msg.what==2){
                        Bundle result = msg.getData();
                        String data = result.getString("Result");
                        Gson gson = new Gson();
                        ResultData  =   gson.fromJson(data, ResultBean.class);
                        adapter.Add(ResultData.getRv());
                  }
            }
      };
      private void onLoad() {
            xListView.stopRefresh();
            xListView.stopLoadMore();
            xListView.setRefreshTime("刚刚");
      }
      @Override
      public void onRefresh() {
            mHandler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                        String url = "http://www.ibianma.com/video/getVideoList.php?type=2&timer=0&dir=pull&num=8";
                        if(NetUtil.isNetworkAvailable(CardActivity.this)) {
                              OkHttpClient mOkHttpClient = new OkHttpClient();
                              final Request request = new Request.Builder()
                                        .url(url)
                                        .build();
                              Call call = mOkHttpClient.newCall(request);
                              call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                          Toast.makeText(CardActivity.this, "网络异常", Toast.LENGTH_LONG).show();
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
                              Toast.makeText(CardActivity.this, "当前网络不可用", Toast.LENGTH_LONG).show();
                              finish();
                        }
                        onLoad();
                  }
            }, 2000);
      }
      @Override
      public void onLoadMore() {
            mHandler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                        if (ResultData.getRv().size()!=0){
                              long time = ResultData.getRv().get(ResultData.getRv().size()-1).getTimer();
                              String url ="http://www.ibianma.com/video/getVideoList.php?type=2&timer="+time+"&dir=push&num=5";
                              OkHttpClient mOkHttpClient = new OkHttpClient();
                              final Request request = new Request.Builder()
                                        .url(url)
                                        .build();
                              Call call = mOkHttpClient.newCall(request);
                              call.enqueue(new Callback()
                              {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                          Toast.makeText(CardActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                          Message message = Message.obtain();
                                          message.what = 2;
                                          Bundle bundleData = new Bundle();
                                          bundleData.putString("Result",  response.body().string());
                                          message.setData(bundleData);
                                          handler.sendMessage(message);
                                    }


                              });
                        }else {
                              Toast.makeText(CardActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                        onLoad();
                  }
            }, 2000);
      }

      @Override
      public void initView() {

      }

      @Override
      public void initData() {

      }

      @Override
      public int setLayout() {
            return R.layout.activity_card;
      }


}
