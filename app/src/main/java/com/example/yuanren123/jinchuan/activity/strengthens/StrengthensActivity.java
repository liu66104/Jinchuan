package com.example.yuanren123.jinchuan.activity.strengthens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.words.BeforeActivity;
import com.example.yuanren123.jinchuan.adapter.strengthens.StrengthensAdapter;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.view.MyGifView;
import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by yuanren123 on 2017/9/19.
 */
@ContentView(R.layout.activity_review)
public class StrengthensActivity extends BaseActivity {

      @ViewInject(R.id.rv_review)
      private RecyclerView recyclerView;
      @ViewInject(R.id.tv_words_show)
      private TextView tv_words_show;
      @ViewInject(R.id.rl_review_one)
      private RelativeLayout rl_one;
      @ViewInject(R.id.rl_review_two)
      private RelativeLayout rl_two;
      @ViewInject(R.id.rl_review_voice)
      private RelativeLayout rl_review_voice;
      @ViewInject(R.id.rl_words_home)
      private RelativeLayout rl_home;
      @ViewInject(R.id.ll_words_show_one)
      private LinearLayout ll_one;
      @ViewInject(R.id.ll_one_japan)
      private TextView tv_one_japan;
      @ViewInject(R.id.ll_one_Chinese)
      private TextView tv_one_Chinese;
      @ViewInject(R.id.ll_words_show_two)
      private LinearLayout ll_two;
      @ViewInject(R.id.ll_two_mean)
      private TextView tv_two;
      @ViewInject(R.id.iv_review_voice)
      private ImageView iv_voice;
      @ViewInject(R.id.gv_review_voice)
      private MyGifView gv_voice;

      private MediaPlayer mediaPlayer;
      private Boolean video = true;
      private StrengthensAdapter adapter;
      private List<ReviewBean> dataBase;
      private List<ReviewBean>  datas;
      private   List<ReviewBean> data;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            SharedPreferences sp = getSharedPreferences("review", Context.MODE_PRIVATE);
            int type = sp.getInt("type", -1);
            data = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
            dataBase = DataSupport.findAll(ReviewBean.class);
            ll_one.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            Collections.shuffle(data);
            datas = random(dataBase,dataBase.size()-1,data.get(0).getJa());
            Collections.shuffle(datas);
            if (type == 0){
                  rl_one.setVisibility(View.VISIBLE);
                  rl_two.setVisibility(View.GONE);
                  adapter = new StrengthensAdapter(this,datas,data.get(0).getJa(),handler,type);
                  recyclerView.setLayoutManager(new LinearLayoutManager(StrengthensActivity.this, LinearLayoutManager.VERTICAL, false));
                  recyclerView.setAdapter(adapter);
                  tv_words_show.setText(data.get(0).getJa());
            }else if (type ==1){
                  rl_one.setVisibility(View.VISIBLE);
                  rl_two.setVisibility(View.GONE);
                  adapter = new StrengthensAdapter(this,datas,data.get(0).getJa(),handler,type);
                  recyclerView.setLayoutManager(new LinearLayoutManager(StrengthensActivity.this, LinearLayoutManager.VERTICAL, false));
                  recyclerView.setAdapter(adapter);
                  tv_words_show.setText(data.get(0).getCh());
            }else if (type ==2){
                  rl_one.setVisibility(View.GONE);
                  rl_two.setVisibility(View.VISIBLE);
                  adapter = new StrengthensAdapter(this,datas,data.get(0).getJa(),handler,0);
                  recyclerView.setLayoutManager(new LinearLayoutManager(StrengthensActivity.this, LinearLayoutManager.VERTICAL, false));
                  recyclerView.setAdapter(adapter);
                  tv_words_show.setText(data.get(0).getJa());
                  iv_voice.setVisibility(View.GONE);
                  gv_voice.setVisibility(View.VISIBLE);
                  gv_voice.setMovieResource(R.drawable.gif1);

                  if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                  }
                  if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                  }

                  video = false;
                  String path=data.get(0).getJaudio();
                  //这里给一个歌曲的网络地址就行了
                  Uri uri  =  Uri.parse(path);
                  mediaPlayer  = MediaPlayer.create(StrengthensActivity.this,uri);
                  mediaPlayer.start();

                  mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                              iv_voice.setVisibility(View.VISIBLE);
                              gv_voice.setVisibility(View.GONE);
                              video = true;
                        }
                  });
                  rl_review_voice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                              if (video){
                                    video = false;
                                    iv_voice.setVisibility(View.GONE);
                                    gv_voice.setVisibility(View.VISIBLE);
                                    gv_voice.setMovieResource(R.drawable.gif1);
                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                          mediaPlayer.stop();
                                    }
                                    if (mediaPlayer != null){
                                          mediaPlayer.release();
                                          mediaPlayer = null;
                                    }
                                    String path=data.get(0).getJaudio();     //这里给一个歌曲的网络地址就行了
                                    Uri uri  =  Uri.parse(path);
                                    mediaPlayer  = MediaPlayer.create(StrengthensActivity.this,uri);
                                    mediaPlayer.start();

                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                          @Override
                                          public void onCompletion(MediaPlayer mediaPlayer) {
                                                iv_voice.setVisibility(View.VISIBLE);
                                                gv_voice.setVisibility(View.GONE);
                                                video = true;
                                          }
                                    });
                              }else {

                              }
                        }
                  });
            }
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
                        SharedPreferences sp = getSharedPreferences("review", Context.MODE_PRIVATE);
                        int type = sp.getInt("type", -1);
                        data = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                        dataBase = DataSupport.findAll(ReviewBean.class);
                        ll_one.setVisibility(View.GONE);
                        ll_two.setVisibility(View.GONE);
                        Collections.shuffle(data);
                        datas = random(dataBase,dataBase.size()-1,data.get(0).getJa());
                        Collections.shuffle(datas);
                        if (type == 0){
                              rl_one.setVisibility(View.VISIBLE);
                              rl_two.setVisibility(View.GONE);
                              adapter = new StrengthensAdapter(StrengthensActivity.this,datas,data.get(0).getJa(),handler,type);
                              recyclerView.setLayoutManager(new LinearLayoutManager(StrengthensActivity.this, LinearLayoutManager.VERTICAL, false));
                              recyclerView.setAdapter(adapter);
                              tv_words_show.setText(data.get(0).getJa());
                        }else if (type ==1){
                              rl_one.setVisibility(View.VISIBLE);
                              rl_two.setVisibility(View.GONE);
                              adapter = new StrengthensAdapter(StrengthensActivity.this,datas,data.get(0).getJa(),handler,type);
                              recyclerView.setLayoutManager(new LinearLayoutManager(StrengthensActivity.this, LinearLayoutManager.VERTICAL, false));
                              recyclerView.setAdapter(adapter);
                              tv_words_show.setText(data.get(0).getCh());
                        }else if (type ==2){
                              rl_one.setVisibility(View.GONE);
                              rl_two.setVisibility(View.VISIBLE);
                              adapter = new StrengthensAdapter(StrengthensActivity.this,datas,data.get(0).getJa(),handler,0);
                              recyclerView.setLayoutManager(new LinearLayoutManager(StrengthensActivity.this, LinearLayoutManager.VERTICAL, false));
                              recyclerView.setAdapter(adapter);
                              tv_words_show.setText(data.get(0).getJa());
                              iv_voice.setVisibility(View.GONE);
                              gv_voice.setVisibility(View.VISIBLE);
                              gv_voice.setMovieResource(R.drawable.gif1);
                              if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                              }
                              if (mediaPlayer != null){
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                              }
                              video = false;
                              String path=data.get(0).getJaudio();     //这里给一个歌曲的网络地址就行了
                              Uri uri  =  Uri.parse(path);
                              mediaPlayer  = MediaPlayer.create(StrengthensActivity.this,uri);
                              mediaPlayer.start();
                              mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                          iv_voice.setVisibility(View.VISIBLE);
                                          gv_voice.setVisibility(View.GONE);
                                          video = true;
                                    }
                              });
                              rl_review_voice.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                                if (video){
                                                      video = false;
                                                      iv_voice.setVisibility(View.GONE);
                                                      gv_voice.setVisibility(View.VISIBLE);
                                                      gv_voice.setMovieResource(R.drawable.gif1);
                                                      if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                                            mediaPlayer.stop();
                                                      }
                                                      if (mediaPlayer != null){
                                                            mediaPlayer.release();
                                                            mediaPlayer = null;
                                                      }
                                                      String path=data.get(0).getJaudio();     //这里给一个歌曲的网络地址就行了
                                                      Uri uri  =  Uri.parse(path);
                                                      mediaPlayer  = MediaPlayer.create(StrengthensActivity.this,uri);
                                                      mediaPlayer.start();

                                                      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(MediaPlayer mediaPlayer) {
                                                                  iv_voice.setVisibility(View.VISIBLE);
                                                                  gv_voice.setVisibility(View.GONE);
                                                                  video = true;
                                                            }
                                                      });
                                                }else {

                                                }
                                          }
                                    });
                        }
                        rl_home.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    finish();
                              }
                        });
                  }else if (msg.what ==1){
                        Intent intent = new Intent(StrengthensActivity.this,BeforeActivity.class);
                        intent.putExtra("Ja",data.get(0).getJa());
                        intent.putExtra("Cx",data.get(0).getCx());
                        intent.putExtra("Pic",data.get(0).getPic());
                        intent.putExtra("Je",data.get(0).getJe());
                        intent.putExtra("Ce",data.get(0).getCe());
                        intent.putExtra("Ch",data.get(0).getCh());
                        intent.putExtra("Jaudio",data.get(0).getJaudio());
                        intent.putExtra("Id",data.get(0).getId());
                        intent.putExtra("Jsentence",data.get(0).getJsentence());
                        intent.putExtra("Jvideo",data.get(0).getJvideo());
                        startActivity(intent);
                  }else if (msg.what ==8){
                        finish();
                  }
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
            return R.layout.activity_review;
      }

      public static  List<ReviewBean> random( List<ReviewBean> dataBase, int maxValue,String NowName){
            int  NowNum = 0;
            int a = dataBase.size();
            for (int i = 0; i <a ; i++) {
                  if (dataBase.get(i).getJa().equals(NowName)){
                        NowNum = i;
                        break;
                  }
            }
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
            List<ReviewBean> result = new ArrayList<>();
            for(int i=0;i<3;i++){
                  result.add(dataBase.get(list.get(i)));
            }
            result.add(dataBase.get(NowNum));
            return result;
      }
      @Override
      protected void onDestroy() {
            super.onDestroy();
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                  mediaPlayer.stop();
            }
            if (mediaPlayer != null){
                  mediaPlayer.release();
                  mediaPlayer = null;
            }
      }

}
