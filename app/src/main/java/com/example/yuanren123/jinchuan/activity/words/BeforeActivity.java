package com.example.yuanren123.jinchuan.activity.words;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.CollectBean;
import com.example.yuanren123.jinchuan.view.MyGifView;
import com.squareup.picasso.Picasso;
import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yuanren123 on 2017/9/18.
 */
@ContentView(R.layout.activity_words_detail)
public class BeforeActivity extends BaseActivity {

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

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            video =true;
            final Intent intent = getIntent();
            final String Ja = intent.getStringExtra("Ja");
            final String Cx = intent.getStringExtra("Cx");
            final String Pic = intent.getStringExtra("Pic");
            final String Je = intent.getStringExtra("Je");
            final String Ce = intent.getStringExtra("Ce");
            final String Ch = intent.getStringExtra("Ch");
            final String Jaudio = intent.getStringExtra("Jaudio");
            final String Jsentence = intent.getStringExtra("Jsentence");
            final String Jvideo = intent.getStringExtra("Jvideo");
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

            rl_continue.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      finish();
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
                        Toast.makeText(BeforeActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
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
                        Calendar calendarToday = Calendar.getInstance();
                        int month = calendarToday.get(Calendar.MONTH) + 1;
                        int day = calendarToday.get(Calendar.DAY_OF_MONTH);
                        int year = calendarToday.get(Calendar.YEAR);
                        collectBean.setTimer(year + "-" + month + "-" + day);
                        collectBean.save();
                        iv_like.setVisibility(View.VISIBLE);
                        iv_ont_like.setVisibility(View.GONE);
                        Toast.makeText(BeforeActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                  }
            });

            rl_detail_voice.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                        if (video){
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
                              String path=Jaudio;     //这里给一个歌曲的网络地址就行了
                              Uri uri  =  Uri.parse(path);
                              mediaPlayer  =MediaPlayer.create(BeforeActivity.this,uri);
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
            rl_detail_jVoice.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (video){

                              iv_voice1.setVisibility(View.GONE);
                              gv_voice1.setVisibility(View.VISIBLE);
                              gv_voice1.setMovieResource(R.drawable.gif1);

                              if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                              }
                              if (mediaPlayer != null){
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                              }
                              video = false;
                              String path=Jsentence;     //这里给一个歌曲的网络地址就行了
                              Uri uri  =  Uri.parse(path);
                              mediaPlayer  =MediaPlayer.create(BeforeActivity.this,uri);
                              mediaPlayer.start();
                              mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
                                          iv_voice1.setVisibility(View.VISIBLE);
                                          gv_voice1.setVisibility(View.GONE);
                                          video = true;
                                    }
                              });

                        }else {

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
