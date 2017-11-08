package com.example.yuanren123.jinchuan.activity.review;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.adapter.review.RecycleReviewAdapter;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.OneWordsBean;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.until.FileUtils;
import com.example.yuanren123.jinchuan.until.NetUtil;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import com.example.yuanren123.jinchuan.view.MyGifView;
import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by yuanren123 on 2017/8/28.
 */
@ContentView(R.layout.activity_review)
public class ReviewActivity extends BaseActivity {

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
      private RecycleReviewAdapter adapter;
      private List<OneWordsBean> dataBase;
      private List<OneWordsBean>  datas;
      private int wordsNum;
      private MediaPlayer mediaPlayer;

      private FileUtils fileUtils = new FileUtils();


      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            dataBase = new ArrayList<>();
            SharedPreferences sp = getSharedPreferences("review", Context.MODE_PRIVATE);
            wordsNum  = sp.getInt("review", 0);
            int type = sp.getInt("type", -1);
            ll_one.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);
            List<ReviewBean>  PhraseBeans = DataSupport.findAll(ReviewBean.class);
            int Pszie = PhraseBeans.size();
            for (int i = 0; i <Pszie ; i++) {
                  OneWordsBean dataBase2 = new OneWordsBean();
                  dataBase2.setJa(PhraseBeans.get(i).getJa());
                  dataBase2.setCx(PhraseBeans.get(i).getCx());
                  dataBase2.setCh(PhraseBeans.get(i).getCh());
                  dataBase2.setPic(PhraseBeans.get(i).getPic());
                  dataBase2.setJe(PhraseBeans.get(i).getJe());
                  dataBase2.setCe(PhraseBeans.get(i).getCe());
                  dataBase2.setJaudio(PhraseBeans.get(i).getJaudio());
                  dataBase2.setJsentence(PhraseBeans.get(i).getJsentence());
                  dataBase2.setJvideo(PhraseBeans.get(i).getJvideo());
                  dataBase2.setTag(PhraseBeans.get(i).getTag());
                  dataBase.add(dataBase2);
            }
            Collections.shuffle(dataBase);
            datas =random(dataBase,PhraseBeans.size(),wordsNum);
            Collections.shuffle(datas);
            if (type == 0){
                  rl_one.setVisibility(View.VISIBLE);
                  rl_two.setVisibility(View.GONE);
                  adapter = new RecycleReviewAdapter(this,datas,dataBase.get(wordsNum).getJa(),wordsNum,handler,type);
                  recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.VERTICAL, false));
                  recyclerView.setAdapter(adapter);
                  if (SharedPreferencesUtils.GetSetShow(this)){
                        String name = dataBase.get(wordsNum).getJa();
                        String jieguo = name.substring(0,name.indexOf("（"));
                        tv_words_show.setText(jieguo);
                  }else {
                        tv_words_show.setText(dataBase.get(wordsNum).getJa());
                  }
            }else if (type ==1){
                  rl_one.setVisibility(View.VISIBLE);
                  rl_two.setVisibility(View.GONE);
                  adapter = new RecycleReviewAdapter(this,datas,dataBase.get(wordsNum).getJa(),wordsNum,handler,type);
                  recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.VERTICAL, false));
                  recyclerView.setAdapter(adapter);
                  tv_words_show.setText(dataBase.get(wordsNum).getCh());
            }else if (type ==2){

                  rl_one.setVisibility(View.GONE);
                  rl_two.setVisibility(View.VISIBLE);
                  adapter = new RecycleReviewAdapter(this,datas,dataBase.get(wordsNum).getJa(),wordsNum,handler,0);
                  recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.VERTICAL, false));
                  recyclerView.setAdapter(adapter);

                  if (SharedPreferencesUtils.GetSetShow(ReviewActivity.this)){
                        String name = dataBase.get(wordsNum).getJa();
                        String jieguo = name.substring(0,name.indexOf("（"));
                        tv_words_show.setText(jieguo);
                  }else {
                        tv_words_show.setText(dataBase.get(wordsNum).getJa());
                  }

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
                  mediaPlayer = new MediaPlayer();
                  boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                  if (exist){
                        try {
                              mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                              mediaPlayer.prepare();
                              mediaPlayer.start();
                              mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                          iv_voice.setVisibility(View.VISIBLE);
                                          gv_voice.setVisibility(View.GONE);
                                    }
                              });
                        } catch (IOException e) {
                              e.printStackTrace();
                        }
                  }else {
                        if (NetUtil.isNetworkAvailable(ReviewActivity.this)){
                              try {
                                    mediaPlayer.setDataSource(dataBase.get(wordsNum).getJaudio());
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                          @Override
                                          public void onCompletion(MediaPlayer mediaPlayer) {
                                                iv_voice.setVisibility(View.VISIBLE);
                                                gv_voice.setVisibility(View.GONE);
                                          }
                                    });
                              } catch (IOException e) {
                                    e.printStackTrace();
                              }
                        }else  {
                              Toast.makeText(ReviewActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                        }
                  }
                  rl_review_voice.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                          iv_voice.setVisibility(View.GONE);
                                          gv_voice.setVisibility(View.VISIBLE);
                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                          mediaPlayer.stop();
                                    }
                                    if (mediaPlayer != null){
                                          mediaPlayer.release();
                                          mediaPlayer = null;
                                    }
                                    mediaPlayer = new MediaPlayer();
                                    boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                                    if (exist){
                                          try {
                                                mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                                                mediaPlayer.prepare();
                                                mediaPlayer.start();
                                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                      @Override
                                                      public void onCompletion(MediaPlayer mediaPlayer) {
                                                            iv_voice.setVisibility(View.VISIBLE);
                                                            gv_voice.setVisibility(View.GONE);

                                                      }
                                                });
                                          } catch (IOException e) {
                                                e.printStackTrace();
                                          }
                                    }else {

                                          if (NetUtil.isNetworkAvailable(ReviewActivity.this)){
                                                try {
                                                      mediaPlayer.setDataSource(dataBase.get(wordsNum).getJaudio());
                                                      mediaPlayer.prepare();
                                                      mediaPlayer.start();
                                                      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(MediaPlayer mediaPlayer) {
                                                                  iv_voice.setVisibility(View.VISIBLE);
                                                                  gv_voice.setVisibility(View.GONE);
                                                            }
                                                      });
                                                } catch (IOException e) {
                                                      e.printStackTrace();
                                                }
                                          }else  {
                                                Toast.makeText(ReviewActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                                          }
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
                  switch (msg.what){
                        case 0:
                              Intent intent = new Intent(ReviewActivity.this,ReviewDetailActivity.class);
                              intent.putExtra("Ja",dataBase.get(wordsNum).getJa());
                              intent.putExtra("Cx",dataBase.get(wordsNum).getCx());
                              intent.putExtra("Pic",dataBase.get(wordsNum).getPic());
                              intent.putExtra("Je",dataBase.get(wordsNum).getJe());
                              intent.putExtra("Ce",dataBase.get(wordsNum).getCe());
                              intent.putExtra("Ch",dataBase.get(wordsNum).getCh());
                              intent.putExtra("Jaudio",dataBase.get(wordsNum).getJaudio());
                              intent.putExtra("Jsentence",dataBase.get(wordsNum).getJsentence());
                              intent.putExtra("Jvideo",dataBase.get(wordsNum).getJvideo());
                              intent.putExtra("Tag",dataBase.get(wordsNum).getTag());
                              startActivity(intent);
                              finish();
                              break;
                        case 1:
                              SharedPreferences sp = getSharedPreferences("review", Context.MODE_PRIVATE);
                              wordsNum  = sp.getInt("review", 0);
                              int type = sp.getInt("type", -1);

                              List<ReviewBean>  PhraseBeans = DataSupport.findAll(ReviewBean.class);
                              datas =random(dataBase,PhraseBeans.size(),wordsNum);
                              Collections.shuffle(datas);

                              if (type == 0){
                                    ll_one.setVisibility(View.GONE);
                                    ll_two.setVisibility(View.GONE);
                                    rl_one.setVisibility(View.VISIBLE);
                                    rl_two.setVisibility(View.GONE);
                                    adapter = new RecycleReviewAdapter(ReviewActivity.this,datas,dataBase.get(wordsNum).getJa(),wordsNum,handler,type);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.VERTICAL, false));
                                    recyclerView.setAdapter(adapter);
                                    if (SharedPreferencesUtils.GetSetShow(ReviewActivity.this)){
                                          String name = dataBase.get(wordsNum).getJa();
                                          String jieguo = name.substring(0,name.indexOf("（"));
                                          tv_words_show.setText(jieguo);
                                    }else {
                                          tv_words_show.setText(dataBase.get(wordsNum).getJa());
                                    }
                              }else if (type ==1){
                                    ll_one.setVisibility(View.GONE);
                                    ll_two.setVisibility(View.GONE);
                                    rl_one.setVisibility(View.VISIBLE);
                                    rl_two.setVisibility(View.GONE);
                                    adapter = new RecycleReviewAdapter(ReviewActivity.this,datas,dataBase.get(wordsNum).getJa(),wordsNum,handler,type);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.VERTICAL, false));
                                    recyclerView.setAdapter(adapter);
                                    tv_words_show.setText(dataBase.get(wordsNum).getCh());
                              }else if (type ==2){
                                    ll_one.setVisibility(View.GONE);
                                    ll_two.setVisibility(View.GONE);
                                    rl_one.setVisibility(View.GONE);
                                    rl_two.setVisibility(View.VISIBLE);
                                    adapter = new RecycleReviewAdapter(ReviewActivity.this,datas,dataBase.get(wordsNum).getJa(),wordsNum,handler,0);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ReviewActivity.this, LinearLayoutManager.VERTICAL, false));
                                    recyclerView.setAdapter(adapter);
                                    if (SharedPreferencesUtils.GetSetShow(ReviewActivity.this)){
                                          String name = dataBase.get(wordsNum).getJa();
                                          String jieguo = name.substring(0,name.indexOf("（"));
                                          tv_words_show.setText(jieguo);
                                    }else {
                                          tv_words_show.setText(dataBase.get(wordsNum).getJa());
                                    }

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
                                    mediaPlayer = new MediaPlayer();
                                    boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                                    if (exist){
                                          try {
                                                mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                                                mediaPlayer.prepare();
                                          } catch (IOException e) {
                                                e.printStackTrace();

                                          }
                                          mediaPlayer.start();
                                          mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mediaPlayer) {
                                                      iv_voice.setVisibility(View.VISIBLE);
                                                      gv_voice.setVisibility(View.GONE);

                                                }
                                          });
                                    }else {
                                          if (NetUtil.isNetworkAvailable(ReviewActivity.this)){
                                                try {
                                                      mediaPlayer.setDataSource(dataBase.get(wordsNum).getJaudio());
                                                      mediaPlayer.prepare();
                                                } catch (IOException e) {
                                                      e.printStackTrace();

                                                }
                                                mediaPlayer.start();
                                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                      @Override
                                                      public void onCompletion(MediaPlayer mediaPlayer) {
                                                            iv_voice.setVisibility(View.VISIBLE);
                                                            gv_voice.setVisibility(View.GONE);

                                                      }
                                                });
                                          }else  {
                                                Toast.makeText(ReviewActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                                          }

                                    }
                                    rl_review_voice.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                                iv_voice.setVisibility(View.GONE);
                                                gv_voice.setVisibility(View.VISIBLE);
                                                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                                      mediaPlayer.stop();
                                                }
                                                if (mediaPlayer != null){
                                                      mediaPlayer.release();
                                                      mediaPlayer = null;
                                                }
                                                mediaPlayer = new MediaPlayer();

                                                boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                                                if (exist){
                                                      try {
                                                            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                                                            Log.d("32dasdxzc", "/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
                                                            mediaPlayer.prepare();
                                                      } catch (IOException e) {
                                                            e.printStackTrace();

                                                      }

                                                      mediaPlayer.start();
                                                      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                            @Override
                                                            public void onCompletion(MediaPlayer mediaPlayer) {
                                                                  iv_voice.setVisibility(View.VISIBLE);
                                                                  gv_voice.setVisibility(View.GONE);

                                                            }
                                                      });
                                                }else {

                                                      if (NetUtil.isNetworkAvailable(ReviewActivity.this)){
                                                            try {
                                                                  mediaPlayer.setDataSource(dataBase.get(wordsNum).getJaudio());
                                                                  mediaPlayer.prepare();
                                                            } catch (IOException e) {
                                                                  e.printStackTrace();

                                                            }
                                                            mediaPlayer.start();
                                                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                                  @Override
                                                                  public void onCompletion(MediaPlayer mediaPlayer) {
                                                                        iv_voice.setVisibility(View.VISIBLE);
                                                                        gv_voice.setVisibility(View.GONE);

                                                                  }
                                                            });
                                                      }else  {
                                                            Toast.makeText(ReviewActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                                                      }
                                                }
                                          }
                                    });




                              }
                              break;
                        case 2:
                              ll_one.setVisibility(View.VISIBLE);
                              tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
                              tv_one_japan.setText(dataBase.get(wordsNum).getJe());
                              ll_two.setVisibility(View.GONE);
                              ReviewBean reviewBean = new ReviewBean();
                              ContentValues values = new ContentValues();
                              values.put("wrongNum", "1");
                              DataSupport.updateAll(ReviewBean.class, values, "ja= ?", dataBase.get(wordsNum).getJa());
                              break;
                        case 3:
                              ll_one.setVisibility(View.GONE);
                              tv_two.setText(dataBase.get(wordsNum).getCx());
                              ll_two.setVisibility(View.VISIBLE);
                              break;
                        case 8:
                              finish();
                        default:
                              break;

                  }
                  super.handleMessage(msg);
            }
      };

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

}
