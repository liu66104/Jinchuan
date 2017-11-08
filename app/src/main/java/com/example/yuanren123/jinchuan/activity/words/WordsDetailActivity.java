package com.example.yuanren123.jinchuan.activity.words;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.MainActivity;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.strengthen.StrengthenActivity;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.CollectBean;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.until.FileUtils;
import com.example.yuanren123.jinchuan.until.NetUtil;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import com.example.yuanren123.jinchuan.view.MyGifView;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by yuanren123 on 2017/8/15.
 */

@ContentView(R.layout.activity_words_detail)
public class WordsDetailActivity extends BaseActivity {

      @ViewInject(R.id.rl_continue)
      private RelativeLayout rl_continue;
      @ViewInject(R.id.tv_words_ci)
      private TextView tv_words_ci;
      @ViewInject(R.id.tv_words_yinbiao)
      private TextView tv_words_yinbiao;
      @ViewInject(R.id.tv_words_fanyi)
      private TextView tv_words_fanyi;
      @ViewInject(R.id.iv_words_pic)
      private ImageView iv_words_pic;
      @ViewInject(R.id.tv_words_liju)
      private TextView tv_words_liju;
      @ViewInject(R.id.tv_words_lijufanyi)
      private TextView tv_words_lijufanyi;
      @ViewInject(R.id.iv_detail_like)
      private ImageView iv_like;
      @ViewInject(R.id.iv_detail_notLike)
      private ImageView iv_ont_like;
      @ViewInject(R.id.rl_detail_voice)
      private RelativeLayout rl_detail_voice;
      @ViewInject(R.id.rl_detail_jVoice)
      private RelativeLayout rl_detail_jVoice;
      @ViewInject(R.id.iv_detail_voice)
      private ImageView iv_voice;
      @ViewInject(R.id.iv_detail_voice1)
      private ImageView iv_voice1;
      @ViewInject(R.id.gv_detail_voice)
      private MyGifView gv_voice;
      @ViewInject(R.id.gv_detail_voice1)
      private MyGifView gv_voice1;
      private MediaPlayer mediaPlayer;
      private FileUtils fileUtils = new FileUtils();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);

            //用来判断是不是已经错了4次，choose等于1时就说明已经错了3次，一直跳转到详细页面。

            final Intent intent = getIntent();
            final int choose = intent.getIntExtra("choose", 0);
            final String Ja = intent.getStringExtra("Ja");
            final String Cx = intent.getStringExtra("Cx");
            final String Pic = intent.getStringExtra("Pic");
            final String Je = intent.getStringExtra("Je");
            final String Ce = intent.getStringExtra("Ce");
            final String Ch = intent.getStringExtra("Ch");
            final String Jaudio = intent.getStringExtra("Jaudio");
            final String Jsentence = intent.getStringExtra("Jsentence");
            final String Jvideo = intent.getStringExtra("Jvideo");
            final int Tag = intent.getIntExtra("tag",0);


            tv_words_ci.setText(Ja);
            tv_words_yinbiao.setText(Cx);
            tv_words_fanyi.setText(Ch);
            Picasso.with(this).load(Pic).into(iv_words_pic);
            tv_words_liju.setText(Je);
            tv_words_lijufanyi.setText(Ce);

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                  mediaPlayer.stop();
            }
            if (mediaPlayer != null){
                  mediaPlayer.release();
                  mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+Tag+".mp3");
            if (exist){
                  try {
                        mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+Tag+".mp3");
                        mediaPlayer.prepare();
                  } catch (IOException e) {
                        e.printStackTrace();
                  }
                  mediaPlayer.start();
            }else {
                  if (NetUtil.isNetworkAvailable(WordsDetailActivity.this)){
                        try {
                              mediaPlayer.setDataSource(Jsentence);
                              mediaPlayer.prepare();
                        } catch (IOException e) {
                              e.printStackTrace();
                        }
                        mediaPlayer.start();
                  }else {
                        Toast.makeText(WordsDetailActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                  }
            }
            SharedPreferences sp = getSharedPreferences("words", Context.MODE_PRIVATE);
            final int wordsNum  = sp.getInt("wordsNum", 0);

            SharedPreferences sp1 = getSharedPreferences("StudyPlan", Context.MODE_PRIVATE);
            final int number = sp1.getInt("wordsNum",8);

            Log.d("awdadsadw", "要学习的个数"+wordsNum);
            Log.d("awdadsadw", "已学习的个数"+number);

            rl_continue.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (choose == 1) {
                              //还是没有选择正确
                              Intent intent = new Intent(WordsDetailActivity.this, ReciteWordsActivity.class);
                              startActivity(intent);
                              finish();
                        } else {
                              if (wordsNum == number ){
                                    ReviewBean reviewBean = new ReviewBean();
                                    List<ReviewBean> data = new ArrayList<ReviewBean>();
                                    data = DataSupport.where("ja= ?", Ja).find(ReviewBean.class);
                                    if (data.size() == 0 || data == null) {
                                          reviewBean.setJa(Ja);
                                          reviewBean.setCx(Cx);
                                          reviewBean.setCh(Ch);
                                          reviewBean.setPic(Pic);
                                          reviewBean.setJe(Je);
                                          reviewBean.setCe(Ce);
                                          reviewBean.setJaudio(Jaudio);
                                          reviewBean.setJsentence(Jsentence);
                                          reviewBean.setJvideo(Jvideo);
                                          reviewBean.setWrongNum(0);
                                          reviewBean.setNowNuM(wordsNum);
                                          reviewBean.setTag(Tag);
                                          reviewBean.save();
                                    }
                                    data = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                                    if (data.size() == 0 || data == null){
                                          Calendar calendarToday = Calendar.getInstance();
                                          int month = calendarToday.get(Calendar.MONTH)+1;
                                          int  day = calendarToday.get(Calendar.DAY_OF_MONTH);
                                          int year = calendarToday.get(Calendar.YEAR);
                                          SharedPreferencesUtils.goComplete(WordsDetailActivity.this,year+""+month+""+day);;
                                          SharedPreferencesUtils.saveStudyProgress(WordsDetailActivity.this,Tag);
                                          Intent intent = new Intent(WordsDetailActivity.this, MainActivity.class);
                                          startActivity(intent);
                                          finish();
                                    }else {
                                          SharedPreferencesUtils.saveStudyProgressTwo(WordsDetailActivity.this,Tag);
                                          Intent intent = new Intent(WordsDetailActivity.this, StrengthenActivity.class);
                                          startActivity(intent);
                                          finish();
                                    }

                              }else {
                                    //选择正确了 把判断值复位 choose = 0
                                    SharedPreferences sp = getSharedPreferences("words", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putInt("choose", 0);
                                    editor.commit();
                                    ReviewBean reviewBean = new ReviewBean();
                                    List<ReviewBean> data = new ArrayList<ReviewBean>();
                                    data = DataSupport.where("ja= ?", Ja).find(ReviewBean.class);
                                    if (data.size() == 0 || data == null) {
                                          reviewBean.setJa(Ja);
                                          reviewBean.setCx(Cx);
                                          reviewBean.setCh(Ch);
                                          reviewBean.setPic(Pic);
                                          reviewBean.setJe(Je);
                                          reviewBean.setCe(Ce);
                                          reviewBean.setJaudio(Jaudio);
                                          reviewBean.setJsentence(Jsentence);
                                          reviewBean.setJvideo(Jvideo);
                                          reviewBean.setWrongNum(0);
                                          reviewBean.setNowNuM(wordsNum);
                                          reviewBean.setTag(Tag);
                                          reviewBean.save();
                                    }
                                    Intent intent = new Intent(WordsDetailActivity.this, ReciteWordsActivity.class);
                                    startActivity(intent);
                                    finish();
                              }


                        }
                  }
            });

            List<CollectBean> data = new ArrayList<CollectBean>();
            data = DataSupport.where("ja= ?", Ja).find(CollectBean.class);
            if (data.size() == 0 || data == null) {
                  iv_like.setVisibility(View.GONE);
                  iv_ont_like.setVisibility(View.VISIBLE);

            } else {
                  iv_like.setVisibility(View.VISIBLE);
                  iv_ont_like.setVisibility(View.GONE);

            }
            iv_like.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        DataSupport.deleteAll(CollectBean.class,"ja =?",Ja);
                        iv_like.setVisibility(View.GONE);
                        iv_ont_like.setVisibility(View.VISIBLE);
                        Toast.makeText(WordsDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                  }
            });

            iv_ont_like.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        CollectBean collectBean = new CollectBean();
                        collectBean.setJa(Ja);
                        collectBean.setCx(Cx);
                        collectBean.setCh(Ch);
                        collectBean.setPic(Pic);
                        collectBean.setJe(Je);
                        collectBean.setCe(Ce);
                        collectBean.setJaudio(Jaudio);
                        collectBean.setJsentence(Jsentence);
                        collectBean.setJvideo(Jvideo);
                        collectBean.setTag(Tag);
                        Calendar calendarToday = Calendar.getInstance();
                        int month = calendarToday.get(Calendar.MONTH) + 1;
                        int day = calendarToday.get(Calendar.DAY_OF_MONTH);
                        int year = calendarToday.get(Calendar.YEAR);
                        collectBean.setTimer(year + "-" + month + "-" + day);
                        collectBean.save();
                        iv_like.setVisibility(View.VISIBLE);
                        iv_ont_like.setVisibility(View.GONE);
                        Toast.makeText(WordsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                  }
            });

            gv_voice.setMovieResource(R.drawable.gif1);
            rl_detail_voice.setOnClickListener(new View.OnClickListener() {
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

                        boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+Tag+".mp3");

                        if (exist){
                              try {
                                    mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+Tag+".mp3");
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

                              if (NetUtil.isNetworkAvailable(WordsDetailActivity.this)){
                                    try {
                                          mediaPlayer.setDataSource(Jaudio);
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
                                    Toast.makeText(WordsDetailActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                              }
                        }


                  }
            });
            gv_voice1.setMovieResource(R.drawable.gif1);
            rl_detail_jVoice.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        iv_voice1.setVisibility(View.GONE);
                        gv_voice1.setVisibility(View.VISIBLE);
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                              mediaPlayer.stop();
                        }
                        if (mediaPlayer != null){
                              mediaPlayer.release();
                              mediaPlayer = null;
                        }
                        mediaPlayer = new MediaPlayer();

                        boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+Tag+".mp3");
                        if (exist){
                              try {
                                    mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+Tag+".mp3");
                                    mediaPlayer.prepare();
                              } catch (IOException e) {

                              }
                              mediaPlayer.start();
                              mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                          iv_voice1.setVisibility(View.VISIBLE);
                                          gv_voice1.setVisibility(View.GONE);
                                    }
                              });
                        }else {
                              if (NetUtil.isNetworkAvailable(WordsDetailActivity.this)){
                                    try {
                                          mediaPlayer.setDataSource(Jsentence);
                                          mediaPlayer.prepare();
                                    } catch (IOException e) {
                                          e.printStackTrace();
                                    }
                                    mediaPlayer.start();
                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                          @Override
                                          public void onCompletion(MediaPlayer mediaPlayer) {
                                                iv_voice1.setVisibility(View.VISIBLE);
                                                gv_voice1.setVisibility(View.GONE);
                                          }
                                    });
                              }else {
                                    Toast.makeText(WordsDetailActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                              }

                        }
                  }
            });
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

      @Override
      public void initView() {

      }
      @Override
      public void initData() {

      }
      @Override
      public int setLayout() {
            return R.layout.activity_words_detail;
      }
}
