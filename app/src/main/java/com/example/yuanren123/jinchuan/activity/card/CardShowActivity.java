package com.example.yuanren123.jinchuan.activity.card;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.adapter.card.TestStackAdapter;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.CardBean;
import com.example.yuanren123.jinchuan.model.SwipeCardBean;
import com.example.yuanren123.jinchuan.until.NetWorkUtils;
import com.google.gson.Gson;
import com.loopeer.cardstack.CardStackView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuanren123 on 2017/10/27.
 */

@ContentView(R.layout.activity_card_show)
public class CardShowActivity extends BaseActivity implements CardStackView.ItemExpendListener{

      @ViewInject(R.id.csv)
      private CardStackView csv;
      @ViewInject(R.id.card_iv_back)
      private ImageView iv_back;
      @ViewInject(R.id.tv_card_title)
      private TextView tv_card_title;

      private TestStackAdapter adapter;
      private List<CardBean> mDatas;
      private SwipeCardBean GsonData;
      public static Integer[] TEST_DATAS = new Integer[]{
                R.color.color_1,
                R.color.color_2,
                R.color.color_5,
                R.color.color_8,
                R.color.color_9,
                R.color.color_10,
                R.color.color_12,
                R.color.color_14,
                R.color.color_17,
                R.color.color_22,
                R.color.color_1,
                R.color.color_2,
                R.color.color_5,
                R.color.color_8,
                R.color.color_9,
                R.color.color_10,
                R.color.color_12,
                R.color.color_14,
                R.color.color_17,
                R.color.color_22,
      };

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            csv.setItemExpendListener(this);
            adapter = new TestStackAdapter(this);
            csv.setAdapter(adapter);
            final Intent intent = getIntent();

            iv_back.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        finish();
                  }
            });

            Thread thread = new Thread(new Runnable() {
                  @Override
                  public void run() {
                        if(NetWorkUtils.isNetworkAvailable(CardShowActivity.this)){
                              OkHttpClient mOkHttpClient = new OkHttpClient();
                              final Request request = new Request.Builder()
                                        .url(intent.getStringExtra("url"))
                                        .build();
                              Call call = mOkHttpClient.newCall(request);
                              call.enqueue(new Callback()
                              {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                          Toast.makeText(CardShowActivity.this, "网络不稳定", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                          Message message = Message.obtain();
                                          message.what = 1;
                                          Bundle bundleData = new Bundle();
                                          bundleData.putString("Result",  response.body().string());
                                          message.setData(bundleData);
                                          handler.sendMessage(message);
                                    }


                              });
                        }else {

                        }
                  }
            });
            thread.start();

      }
      Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                  Bundle result = msg.getData();
                  String data = result.getString("Result");
                  Gson gson = new Gson();
                  GsonData  =   gson.fromJson(data, SwipeCardBean.class);
                  int a = GsonData.getList().size();
                  mDatas = new ArrayList<>();
                  tv_card_title.setText("单词"+GsonData.getName());
                  for (int i = 0; i <a ; i++) {
                        CardBean wordsbean = new CardBean();
                        wordsbean.setJa( GsonData.getList().get(i).getJa());
                        wordsbean.setCx(GsonData.getList().get(i).getCx());
                        wordsbean.setPic(GsonData.getList().get(i).getPic());
                        wordsbean.setTag(i+1+"");
                        wordsbean.setJe( GsonData.getList().get(i).getJe());
                        wordsbean.setJaudio(GsonData.getList().get(i).getJaudio());
                        wordsbean.setCe(GsonData.getList().get(i).getCe());
                        wordsbean.setColor(TEST_DATAS[i]);
                        wordsbean.setJsentence(GsonData.getList().get(i).getJsentence());
                        mDatas.add(wordsbean);
                  }
                  adapter.updateData(mDatas);
                  super.handleMessage(msg);
            }
      };

      @Override
      public void initView() {

      }

      @Override
      public void initData() {

      }

      @Override
      public int setLayout() {
            return R.layout.activity_card_show;
      }

      @Override
      public void onItemExpend(boolean expend) {

      }
}
