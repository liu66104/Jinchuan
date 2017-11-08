package com.example.yuanren123.jinchuan.activity.strengthen;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.MainActivity;
import com.example.yuanren123.jinchuan.R;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yuanren123 on 2017/9/14.
 */
@ContentView(R.layout.activity_words_detail)
public class StrengthenDetailActivity extends BaseActivity {

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
      private Boolean video;
      private FileUtils fileUtils = new FileUtils();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //用来判断是不是已经错了4次，choose等于1时就说明已经错了3次，一直跳转到详细页面。
            video =true;


            final Intent intent = getIntent();
            final int choose = intent.getIntExtra("number", 0);

            final String Ja = intent.getStringExtra("Ja");
            final String Cx = intent.getStringExtra("Cx");
            final String Pic = intent.getStringExtra("Pic");
            final String Je = intent.getStringExtra("Je");
            final String Ce = intent.getStringExtra("Ce");
            final String Ch = intent.getStringExtra("Ch");
            final String Jaudio = intent.getStringExtra("Jaudio");
            final String Jsentence = intent.getStringExtra("Jsentence");
            final String Jvideo = intent.getStringExtra("Jvideo");
            final int Tag = intent.getIntExtra("Tag",0);

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
                  if (NetUtil.isNetworkAvailable(StrengthenDetailActivity.this)){
                        try {
                              mediaPlayer.setDataSource(Jsentence);
                              mediaPlayer.prepare();
                        } catch (IOException e) {
                              e.printStackTrace();
                        }
                        mediaPlayer.start();
                  }else {
                        Toast.makeText(StrengthenDetailActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                  }
            }
            rl_continue.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (choose == 0) {
                              //还是没有选择正确
                              sendBroadcast();
                              finish();
                        } else if (choose== 1){
                              ContentValues values = new ContentValues();
                              values.put("wrongNum", "0");
                              DataSupport.updateAll(ReviewBean.class, values, "ja= ?", Ja);

                              List<ReviewBean> data = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                              Log.d("32132xzdsad32", data.size()+"!!!!!");

                              if (data.size() == 0 || data == null){
                                    Calendar calendarToday = Calendar.getInstance();
                                    int month = calendarToday.get(Calendar.MONTH)+1;
                                    int  day = calendarToday.get(Calendar.DAY_OF_MONTH);
                                    int year = calendarToday.get(Calendar.YEAR);
                                    SharedPreferencesUtils.goComplete(StrengthenDetailActivity.this,year+""+month+""+day);
                                    int Progress = SharedPreferencesUtils.getStudyProgressTwo(StrengthenDetailActivity.this);
                                    SharedPreferencesUtils.saveStudyProgress(StrengthenDetailActivity.this,Progress);
                                    Intent intent = new Intent(StrengthenDetailActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();


                              }else {
                                    sendBroadcast();
                                    finish();
                              }


                        }else if (choose == 2){
                              Intent intent = new Intent(StrengthenDetailActivity.this, MainActivity.class);
                              startActivity(intent);
                              List<ReviewBean> data;data = DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                              if (data.size() == 0 || data == null){

                              }else {

                              }
                              finish();
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
                        Toast.makeText(StrengthenDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(StrengthenDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

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
                              if (NetUtil.isNetworkAvailable(StrengthenDetailActivity.this)){
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
                                    Toast.makeText(StrengthenDetailActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
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
                              if (NetUtil.isNetworkAvailable(StrengthenDetailActivity.this)){
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
                                    Toast.makeText(StrengthenDetailActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                              }
                        }
                  }
            });
      }

      public void sendBroadcast() {
            Intent intent = new Intent("chooseRight");
            Log.d("dasdwdasd", "发送");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

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
