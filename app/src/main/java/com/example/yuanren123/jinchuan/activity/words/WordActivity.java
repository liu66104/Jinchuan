//package com.example.yuanren123.jinchuan.activity.words;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.v7.widget.RecyclerView;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.ForegroundColorSpan;
//import android.util.Log;
//import android.view.View;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.example.yuanren123.jinchuan.MainActivity;
//import com.example.yuanren123.jinchuan.R;
//import com.example.yuanren123.jinchuan.adapter.words.GridViewAdapter;
//import com.example.yuanren123.jinchuan.base.BaseActivity;
//import com.example.yuanren123.jinchuan.model.OneWordsBean;
//import com.example.yuanren123.jinchuan.model.PhraseBean;
//import com.example.yuanren123.jinchuan.model.ReviewBean;
//import com.example.yuanren123.jinchuan.until.NetUtil;
//import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
//import com.example.yuanren123.jinchuan.view.MyGifView;
//import org.litepal.crud.DataSupport;
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.ViewInject;
//import org.xutils.x;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by yuanren123 on 2017/10/11.
// */
//@ContentView(R.layout.activity_words)
//public class WordActivity extends BaseActivity {
//
//
//      @ViewInject(R.id.gv_words)
//      private GridView gridView;
//      @ViewInject(R.id.rl_words_detail)
//      private RelativeLayout rl_words_detail;
//      @ViewInject(R.id.rl_words_home)
//      private RelativeLayout rl_words_home;
//      @ViewInject(R.id.ll_words_show_one)
//      private LinearLayout ll_one;
//      @ViewInject(R.id.ll_words_show_two)
//      private LinearLayout ll_two;
//      @ViewInject(R.id.ll_words_show_three)
//      private LinearLayout ll_three;
//      @ViewInject(R.id.tv_words_show)
//      private TextView tv_words_show;
//      @ViewInject(R.id.rl_words_voice)
//      private RelativeLayout rl_voice;
//      @ViewInject(R.id.rl_words_prompt)
//      private TextView rl_prompt;
//      //   private WordsBean data;
//      @ViewInject(R.id.ll_one_japan)
//      private TextView tv_one_japan;
//      @ViewInject(R.id.ll_one_Chinese)
//      private TextView tv_one_Chinese;
//      @ViewInject(R.id.ll_two_name)
//      private TextView tv_two_name;
//      @ViewInject(R.id.ll_two_mean)
//      private TextView tv_two_mean;
//      @ViewInject(R.id.tv_need_lean)
//      private TextView tv_need_lean;
//      @ViewInject(R.id.tv_need_review)
//      private TextView tv_need_review;
//      @ViewInject(R.id.ll_words_main_show)
//      private LinearLayout ll_main;
//      @ViewInject(R.id.rl_words_main_show)
//      private RelativeLayout rl_main;
//      @ViewInject(R.id.tv_show_noWords)
//      private RelativeLayout tv_show_noWords;
//      @ViewInject(R.id.iv_word_voice)
//      private ImageView iv_voice;
//      @ViewInject(R.id.gv_word_voice)
//      private MyGifView gv_voice;
//      @ViewInject(R.id.rl_sentence_show)
//      private RelativeLayout rl_sentence_show;
//      @ViewInject(R.id.tv_sentence_show)
//      private TextView tv_sentence_show;
//      @ViewInject(R.id.tv_word_beforeWords)
//      private TextView tv_word_beforeWords;
//      @ViewInject(R.id.rv_word)
//      private RecyclerView recyclerView;
//
//      private int wordsNum;
//      private int choose;
//      private MediaPlayer mediaPlayer;
//      private List<OneWordsBean> datas;
//      private List<OneWordsBean> dataBase;
//      private Boolean outOfBounds = true;
//      private GridViewAdapter adapter;
//
//
//      @Override
//      protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            x.view().inject(this);
//
//            SharedPreferences sp = getSharedPreferences("words", Context.MODE_PRIVATE);
//            wordsNum = sp.getInt("wordsNum", 0);
//            choose = sp.getInt("judge", 0);
//            List<ReviewBean> reviewBean = DataSupport.findAll(ReviewBean.class);
//            tv_need_review.setText("需复习:" + reviewBean.size());
//
//
//            int Progress = SharedPreferencesUtils.getStudyProgress(WordActivity.this);
//            Log.d("xuedaoledijigedanci", Progress + "");
//
//            /*
//             从数据库取出单词
//             */
//            //取计划学几个单词
//
//            SharedPreferences sp1 = getSharedPreferences("StudyPlan", Context.MODE_PRIVATE);
//            int number = sp1.getInt("wordsNum", 8);
//            dataBase = new ArrayList<>();
//
//            for (int i = 0; i < number; i++) {
//                  List<PhraseBean> PhraseBeans = DataSupport.where("Tag = ?", Progress + 1 + i + "").find(PhraseBean.class);
//                  OneWordsBean dataBase2 = new OneWordsBean();
//                  try {
//                        if (PhraseBeans.get(0).getShowType() == 0){
//                              dataBase2.setJa(PhraseBeans.get(0).getJa());
//                              dataBase2.setCx(PhraseBeans.get(0).getCx());
//                              dataBase2.setCh(PhraseBeans.get(0).getCh());
//                              dataBase2.setPic(PhraseBeans.get(0).getPic());
//                              dataBase2.setJe(PhraseBeans.get(0).getJe());
//                              dataBase2.setCe(PhraseBeans.get(0).getCe());
//                              dataBase2.setJaudio(PhraseBeans.get(0).getJaudio());
//                              dataBase2.setJsentence(PhraseBeans.get(0).getJsentence());
//                              dataBase2.setJvideo(PhraseBeans.get(0).getJvideo());
//                              dataBase2.setId(PhraseBeans.get(0).getId());
//                              dataBase2.setTag(PhraseBeans.get(0).getTag());
//                              dataBase2.setType(PhraseBeans.get(0).getType());
//                              dataBase2.setShowType(0);
//                              dataBase.add(dataBase2);
//                        }
//                        else {
//                              Toast.makeText(this, "学完了", Toast.LENGTH_SHORT).show();
//                        }
//
//                  } catch (Exception e) {
//                        outOfBounds = false;
//                        break;
//                  }
//            }
//            /*
//            * 检测单词是否都背完
//             */
//
//            if (outOfBounds) {
//                  if (wordsNum == 0){
//                        tv_word_beforeWords.setVisibility(View.GONE);
//                  }else {
//                        tv_word_beforeWords.setVisibility(View.VISIBLE);
//                        final int wordsNumBefore = wordsNum-1;
//                        tv_word_beforeWords.setText(dataBase.get(wordsNumBefore).getJa()+dataBase.get(wordsNumBefore).getCx()+dataBase.get(wordsNumBefore).getCh());
//                        tv_word_beforeWords.setOnClickListener(new View.OnClickListener() {
//                              @Override
//                              public void onClick(View view) {
//                                    Intent intent = new Intent(WordActivity.this,BeforeActivity.class);
//                                    intent.putExtra("Ja",dataBase.get(wordsNumBefore).getJa());
//                                    intent.putExtra("Cx",dataBase.get(wordsNumBefore).getCx());
//                                    intent.putExtra("Pic",dataBase.get(wordsNumBefore).getPic());
//                                    intent.putExtra("Je",dataBase.get(wordsNumBefore).getJe());
//                                    intent.putExtra("Ce",dataBase.get(wordsNumBefore).getCe());
//                                    intent.putExtra("Ch",dataBase.get(wordsNumBefore).getCh());
//                                    intent.putExtra("Jaudio",dataBase.get(wordsNumBefore).getJaudio());
//                                    intent.putExtra("Id",dataBase.get(wordsNumBefore).getId());
//                                    intent.putExtra("Jsentence",dataBase.get(wordsNumBefore).getJsentence());
//                                    intent.putExtra("Jvideo",dataBase.get(wordsNumBefore).getJvideo());
//                                    intent.putExtra("tag",dataBase.get(wordsNumBefore).getTag());
//                                    startActivity(intent);
//                              }
//                        });
//                  }
//
//                  int showType = dataBase.get(wordsNum).getShowType();
//                  switch (showType){
//                        case 0:
//                              recyclerView.setVisibility(View.GONE);
//                              gridView.setVisibility(View.VISIBLE);
//                              int showWordsType = dataBase.get(wordsNum).getType();
//                              if (showWordsType == 1){
//                                    rl_sentence_show.setVisibility(View.GONE);
//                                    //播放词的音频
//                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                          mediaPlayer.stop();
//                                    }else {
//
//                                    }
//                                    if (mediaPlayer != null){
//                                          mediaPlayer.release();
//                                          mediaPlayer = null;
//                                    }
//                                    mediaPlayer = new MediaPlayer();
//                                    try {
//                                          mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
//                                          mediaPlayer.prepare();
//                                    } catch (IOException e) {
//                                          e.printStackTrace();
//
//                                    }
//                                    mediaPlayer.seekTo(400);
//                                    mediaPlayer.start();
//                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                          @Override
//                                          public void onCompletion(MediaPlayer mediaPlayer) {
//                                                iv_voice.setVisibility(View.VISIBLE);
//                                                gv_voice.setVisibility(View.GONE);
//
//                                          }
//                                    });
//
//
//                                    //显示当前需要新学单词个数
//                                    int need_lean = number-wordsNum;
//                                    tv_need_lean.setText("需新学:"+need_lean);
//
//                                    datas = random(dataBase,dataBase.size()-1,wordsNum);
//                                    Collections.shuffle(datas);
//                                    adapter = new GridViewAdapter(WordActivity.this,handler,datas,dataBase.get(wordsNum).getJa(),wordsNum,choose);
//                                    gridView.setAdapter(adapter);
//
//                                    //加载页面提示信息
//                                    if (SharedPreferencesUtils.GetSetShow(this)){
//                                          String name = dataBase.get(wordsNum).getJa();
//                                          String jieguo = name.substring(0,name.indexOf("（"));
//                                          tv_words_show.setText(jieguo);
//                                    }else {
//                                          tv_words_show.setText(dataBase.get(wordsNum).getJa());
//                                    }
//                                    tv_one_japan.setText(dataBase.get(wordsNum).getJe());
//                                    tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
//                                    tv_two_name.setText(dataBase.get(wordsNum).getCh());
//                                    tv_two_mean.setText(dataBase.get(wordsNum).getCx());
//
//                                    rl_words_home.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                Intent intent = new Intent(WordActivity.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                          }
//                                    });
//                                    gv_voice.setMovieResource(R.drawable.gif1);
//                                    rl_voice.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//
//                                                if (NetUtil.isNetworkAvailable(WordActivity.this)){
//                                                      iv_voice.setVisibility(View.GONE);
//                                                      gv_voice.setVisibility(View.VISIBLE);
//
//                                                      if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                                            mediaPlayer.stop();
//                                                      }else {
//
//                                                      }
//                                                      if (mediaPlayer != null){
//                                                            mediaPlayer.release();
//                                                            mediaPlayer = null;
//                                                      }
//
//
////                                                            String path="/jinchuanxiaociMp3/1.mp3";     //这里给一个歌曲的网络地址就行了
////                                                            Uri uri  =  Uri.parse(path);
//                                                      mediaPlayer = new MediaPlayer();
//                                                      try {
//                                                            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
//                                                            mediaPlayer.prepare();
//                                                      } catch (IOException e) {
//                                                            e.printStackTrace();
//
//                                                      }
//                                                      mediaPlayer.seekTo(400);
//                                                      mediaPlayer.start();
//                                                      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                                            @Override
//                                                            public void onCompletion(MediaPlayer mediaPlayer) {
//                                                                  iv_voice.setVisibility(View.VISIBLE);
//                                                                  gv_voice.setVisibility(View.GONE);
//
//                                                            }
//                                                      });
//
//                                                }else {
//                                                      Toast.makeText(WordActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                          }
//                                    });
//
//                                    rl_prompt.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                handler.sendEmptyMessage(0);
//                                          }
//                                    });
//                              }else if (showWordsType ==2){
//                                    String name = dataBase.get(wordsNum).getJa();
//                                    String jieguo = name.substring(name.indexOf("（")+1,name.indexOf("）"));
//                                    String sentence = dataBase.get(wordsNum).getJe();
//                                    SpannableString s = new SpannableString(sentence);
//                                    Pattern p = Pattern.compile(jieguo);
//                                    Matcher m = p.matcher(s);
//                                    while (m.find()) {
//                                          int start = m.start();
//                                          int end = m.end();
//                                          s.setSpan(new ForegroundColorSpan(Color.parseColor("#7EAEDC")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    }
//                                    tv_sentence_show.setText(s);
//                                    rl_sentence_show.setVisibility(View.VISIBLE);
//                                    tv_words_show.setVisibility(View.INVISIBLE);
//                                    //播放词的音频
//                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                          mediaPlayer.stop();
//                                    }
//                                    if (mediaPlayer != null){
//                                          mediaPlayer.release();
//                                          mediaPlayer = null;
//                                    }
//                                    mediaPlayer = new MediaPlayer();
//                                    try {
//                                          mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+dataBase.get(wordsNum).getTag()+".mp3");
//                                          mediaPlayer.prepare();
//                                    } catch (IOException e) {
//                                          e.printStackTrace();
//
//                                    }
//                                    mediaPlayer.seekTo(400);
//                                    mediaPlayer.start();
//                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                          @Override
//                                          public void onCompletion(MediaPlayer mediaPlayer) {
//                                                iv_voice.setVisibility(View.VISIBLE);
//                                                gv_voice.setVisibility(View.GONE);
//
//                                          }
//                                    });
//                                    //显示当前需要新学单词个数
//                                    int need_lean = number-wordsNum;
//                                    tv_need_lean.setText("需新学:"+need_lean);
//
//                                    datas = random(dataBase,dataBase.size()-1,wordsNum);
//                                    Collections.shuffle(datas);
//                                    adapter = new GridViewAdapter(WordActivity.this,handler,datas,dataBase.get(wordsNum).getJa(),wordsNum,choose);
//                                    gridView.setAdapter(adapter);
//
//                                    //加载页面提示信息
//                                    tv_words_show.setText(dataBase.get(wordsNum).getJa());
//                                    tv_one_japan.setText(dataBase.get(wordsNum).getJe());
//                                    tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
//                                    tv_two_name.setText(dataBase.get(wordsNum).getCh());
//                                    tv_two_mean.setText(dataBase.get(wordsNum).getCx());
//
//                                    rl_words_home.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                Intent intent = new Intent(WordActivity.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                          }
//                                    });
//                                    rl_voice.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                                      mediaPlayer.stop();
//                                                }
//                                                if (mediaPlayer != null){
//                                                      mediaPlayer.release();
//                                                      mediaPlayer = null;
//                                                }
//                                                mediaPlayer = new MediaPlayer();
//                                                try {
//                                                      mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
//                                                      mediaPlayer.prepare();
//                                                } catch (IOException e) {
//                                                      e.printStackTrace();
//
//                                                }
//                                                mediaPlayer.seekTo(400);
//                                                mediaPlayer.start();
//                                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                                      @Override
//                                                      public void onCompletion(MediaPlayer mediaPlayer) {
//                                                            iv_voice.setVisibility(View.VISIBLE);
//                                                            gv_voice.setVisibility(View.GONE);
//
//                                                      }
//                                                });
//                                          }
//                                    });
//
//                                    rl_prompt.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                handler.sendEmptyMessage(0);
//                                          }
//                                    });
//
//                              }
//                              tv_show_noWords.setVisibility(View.GONE);
//                              break;
//                        case 1:
//                              recyclerView.setVisibility(View.GONE);
//                              gridView.setVisibility(View.VISIBLE);
//                              int showWordsType1 = dataBase.get(wordsNum).getType();
//                              if (showWordsType1 == 1){
//                                    rl_sentence_show.setVisibility(View.GONE);
//                                    //播放词的音频
//                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                          mediaPlayer.stop();
//                                    }else {
//
//                                    }
//                                    if (mediaPlayer != null){
//                                          mediaPlayer.release();
//                                          mediaPlayer = null;
//                                    }
//                                    mediaPlayer = new MediaPlayer();
//                                    try {
//                                          mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
//                                          mediaPlayer.prepare();
//                                    } catch (IOException e) {
//                                          e.printStackTrace();
//
//                                    }
//                                    mediaPlayer.seekTo(400);
//                                    mediaPlayer.start();
//                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                          @Override
//                                          public void onCompletion(MediaPlayer mediaPlayer) {
//                                                iv_voice.setVisibility(View.VISIBLE);
//                                                gv_voice.setVisibility(View.GONE);
//
//                                          }
//                                    });
//
//
//                                    //显示当前需要新学单词个数
//                                    int need_lean = number-wordsNum;
//                                    tv_need_lean.setText("需新学:"+need_lean);
//
//                                    datas = random(dataBase,dataBase.size()-1,wordsNum);
//                                    Collections.shuffle(datas);
//                                    adapter = new GridViewAdapter(WordActivity.this,handler,datas,dataBase.get(wordsNum).getJa(),wordsNum,choose);
//                                    gridView.setAdapter(adapter);
//
//                                    //加载页面提示信息
//                                    if (SharedPreferencesUtils.GetSetShow(this)){
//                                          String name = dataBase.get(wordsNum).getJa();
//                                          String jieguo = name.substring(0,name.indexOf("（"));
//                                          tv_words_show.setText(jieguo);
//                                    }else {
//                                          tv_words_show.setText(dataBase.get(wordsNum).getJa());
//                                    }
//                                    tv_one_japan.setText(dataBase.get(wordsNum).getJe());
//                                    tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
//                                    tv_two_name.setText(dataBase.get(wordsNum).getCh());
//                                    tv_two_mean.setText(dataBase.get(wordsNum).getCx());
//
//                                    rl_words_home.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                Intent intent = new Intent(WordActivity.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                          }
//                                    });
//                                    gv_voice.setMovieResource(R.drawable.gif1);
//                                    rl_voice.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//
//                                                if (NetUtil.isNetworkAvailable(WordActivity.this)){
//                                                      iv_voice.setVisibility(View.GONE);
//                                                      gv_voice.setVisibility(View.VISIBLE);
//
//                                                      if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                                            mediaPlayer.stop();
//                                                      }else {
//
//                                                      }
//                                                      if (mediaPlayer != null){
//                                                            mediaPlayer.release();
//                                                            mediaPlayer = null;
//                                                      }
//
//
////                                                            String path="/jinchuanxiaociMp3/1.mp3";     //这里给一个歌曲的网络地址就行了
////                                                            Uri uri  =  Uri.parse(path);
//                                                      mediaPlayer = new MediaPlayer();
//                                                      try {
//                                                            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
//                                                            mediaPlayer.prepare();
//                                                      } catch (IOException e) {
//                                                            e.printStackTrace();
//
//                                                      }
//                                                      mediaPlayer.seekTo(400);
//                                                      mediaPlayer.start();
//                                                      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                                            @Override
//                                                            public void onCompletion(MediaPlayer mediaPlayer) {
//                                                                  iv_voice.setVisibility(View.VISIBLE);
//                                                                  gv_voice.setVisibility(View.GONE);
//
//                                                            }
//                                                      });
//
//                                                }else {
//                                                      Toast.makeText(WordActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                          }
//                                    });
//
//                                    rl_prompt.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                handler.sendEmptyMessage(0);
//                                          }
//                                    });
//                              }else if (showWordsType1 ==2){
//                                    String name = dataBase.get(wordsNum).getJa();
//                                    String jieguo = name.substring(name.indexOf("（")+1,name.indexOf("）"));
//                                    String sentence = dataBase.get(wordsNum).getJe();
//                                    SpannableString s = new SpannableString(sentence);
//                                    Pattern p = Pattern.compile(jieguo);
//                                    Matcher m = p.matcher(s);
//                                    while (m.find()) {
//                                          int start = m.start();
//                                          int end = m.end();
//                                          s.setSpan(new ForegroundColorSpan(Color.parseColor("#7EAEDC")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                    }
//                                    tv_sentence_show.setText(s);
//                                    rl_sentence_show.setVisibility(View.VISIBLE);
//                                    tv_words_show.setVisibility(View.INVISIBLE);
//                                    //播放词的音频
//                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                          mediaPlayer.stop();
//                                    }
//                                    if (mediaPlayer != null){
//                                          mediaPlayer.release();
//                                          mediaPlayer = null;
//                                    }
//                                    mediaPlayer = new MediaPlayer();
//                                    try {
//                                          mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+dataBase.get(wordsNum).getTag()+".mp3");
//                                          mediaPlayer.prepare();
//                                    } catch (IOException e) {
//                                          e.printStackTrace();
//
//                                    }
//                                    mediaPlayer.seekTo(400);
//                                    mediaPlayer.start();
//                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                          @Override
//                                          public void onCompletion(MediaPlayer mediaPlayer) {
//                                                iv_voice.setVisibility(View.VISIBLE);
//                                                gv_voice.setVisibility(View.GONE);
//
//                                          }
//                                    });
//                                    //显示当前需要新学单词个数
//                                    int need_lean = number-wordsNum;
//                                    tv_need_lean.setText("需新学:"+need_lean);
//
//                                    datas = random(dataBase,dataBase.size()-1,wordsNum);
//                                    Collections.shuffle(datas);
//                                    adapter = new GridViewAdapter(WordActivity.this,handler,datas,dataBase.get(wordsNum).getJa(),wordsNum,choose);
//                                    gridView.setAdapter(adapter);
//
//                                    //加载页面提示信息
//                                    tv_words_show.setText(dataBase.get(wordsNum).getJa());
//                                    tv_one_japan.setText(dataBase.get(wordsNum).getJe());
//                                    tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
//                                    tv_two_name.setText(dataBase.get(wordsNum).getCh());
//                                    tv_two_mean.setText(dataBase.get(wordsNum).getCx());
//
//                                    rl_words_home.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                Intent intent = new Intent(WordActivity.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                          }
//                                    });
//                                    rl_voice.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                                      mediaPlayer.stop();
//                                                }
//                                                if (mediaPlayer != null){
//                                                      mediaPlayer.release();
//                                                      mediaPlayer = null;
//                                                }
//                                                mediaPlayer = new MediaPlayer();
//                                                try {
//                                                      mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+dataBase.get(wordsNum).getTag()+".mp3");
//                                                      mediaPlayer.prepare();
//                                                } catch (IOException e) {
//                                                      e.printStackTrace();
//
//                                                }
//                                                mediaPlayer.seekTo(400);
//                                                mediaPlayer.start();
//                                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                                      @Override
//                                                      public void onCompletion(MediaPlayer mediaPlayer) {
//                                                            iv_voice.setVisibility(View.VISIBLE);
//                                                            gv_voice.setVisibility(View.GONE);
//
//                                                      }
//                                                });
//                                          }
//                                    });
//
//                                    rl_prompt.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View view) {
//                                                handler.sendEmptyMessage(0);
//                                          }
//                                    });
//
//                              }
//                              tv_show_noWords.setVisibility(View.GONE);
//                              break;
//                        case 2:
//                              recyclerView.setVisibility(View.VISIBLE);
//                              gridView.setVisibility(View.GONE);
//
//
//                              break;
//                        case 3:
//                              break;
//
//                  }
//
//
//            } else {
//                  tv_show_noWords.setVisibility(View.VISIBLE);
//                  Toast.makeText(this, "您已经学完我们目前的单词了", Toast.LENGTH_SHORT).show();
//            }
//
//
//      }
//
//
//      public static  List<OneWordsBean> random( List<OneWordsBean> dataBase, int maxValue,int NowNum){
//            List<Integer> list = new ArrayList<Integer>();
//            Random r = new Random();
//            while(list.size()<3)
//            {
//                  int num = r.nextInt(maxValue);
//                  Log.d("sdadawdwa", "运行1"+ num);
//                  if (!list.contains(num)&&num !=NowNum) {
//                        Log.d("sdadawdwa", num+"!");
//                        list.add(num);
//                  }
//            }
//            List<OneWordsBean> result = new ArrayList<>();
//            for(int i=0;i<3;i++){
//                  result.add(dataBase.get(list.get(i)));
//            }
//            result.add(dataBase.get(NowNum));
//            return result;
//      }
//
//
//      @Override
//      public void initView() {
//
//      }
//
//      @Override
//      public void initData() {
//
//      }
//
//      @Override
//      public int setLayout() {
//
//            return R.layout.activity_words;
//      }
//}
