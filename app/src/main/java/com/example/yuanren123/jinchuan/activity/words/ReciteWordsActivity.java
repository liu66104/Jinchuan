package com.example.yuanren123.jinchuan.activity.words;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.MainActivity;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.adapter.words.GridViewAdapter;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.OneWordsBean;
import com.example.yuanren123.jinchuan.model.PhraseBean;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuanren123 on 2017/9/18.
 */

@ContentView(R.layout.activity_words)
public class ReciteWordsActivity extends BaseActivity {

      @ViewInject(R.id.gv_words)
      private GridView gridView;
      @ViewInject(R.id.rl_words_detail)
      private RelativeLayout rl_words_detail;
      @ViewInject(R.id.rl_words_home)
      private RelativeLayout rl_words_home;
      @ViewInject(R.id.ll_words_show_one)
      private LinearLayout ll_one;
      @ViewInject(R.id.ll_words_show_two)
      private LinearLayout ll_two;
      @ViewInject(R.id.ll_words_show_three)
      private LinearLayout ll_three;
      @ViewInject(R.id.tv_words_show)
      private TextView tv_words_show;
      @ViewInject(R.id.rl_words_voice)
      private RelativeLayout rl_voice;
      @ViewInject(R.id.rl_words_prompt)
      private TextView rl_prompt;
      //   private WordsBean data;
      @ViewInject(R.id.ll_one_japan)
      private TextView tv_one_japan;
      @ViewInject(R.id.ll_one_Chinese)
      private TextView tv_one_Chinese;
      @ViewInject(R.id.ll_two_name)
      private TextView tv_two_name;
      @ViewInject(R.id.ll_two_mean)
      private TextView tv_two_mean;
      @ViewInject(R.id.tv_need_lean)
      private TextView tv_need_lean;
      @ViewInject(R.id.tv_need_review)
      private TextView tv_need_review;
      @ViewInject(R.id.ll_words_main_show)
      private LinearLayout ll_main;
      @ViewInject(R.id.rl_words_main_show)
      private RelativeLayout rl_main;
      @ViewInject(R.id.tv_show_noWords)
      private RelativeLayout tv_show_noWords;
      @ViewInject(R.id.iv_word_voice)
      private ImageView iv_voice;
      @ViewInject(R.id.gv_word_voice)
      private MyGifView gv_voice;
      @ViewInject(R.id.rl_sentence_show)
      private RelativeLayout rl_sentence_show;
      @ViewInject(R.id.tv_sentence_show)
      private TextView tv_sentence_show;
      @ViewInject(R.id.tv_word_beforeWords)
      private TextView tv_word_beforeWords;
      private int wordsNum;
      private int choose;
      private MediaPlayer mediaPlayer;
      private List<OneWordsBean> datas;
      private List<OneWordsBean> dataBase;
      private Boolean outOfBounds = true;
      private GridViewAdapter adapter;
      private  FileUtils fileUtils = new FileUtils();

      @Override
      protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            x.view().inject(this);
            //取学到了第几个单词
            SharedPreferences sp = getSharedPreferences("words", Context.MODE_PRIVATE);
            wordsNum  = sp.getInt("wordsNum", 0);
            choose = sp.getInt("judge",0);
            List<ReviewBean> reviewBean = DataSupport.findAll(ReviewBean.class);
            tv_need_review.setText("需复习:"+reviewBean.size());
            //取计划学几个单词
            SharedPreferences sp1 = getSharedPreferences("StudyPlan", Context.MODE_PRIVATE);
            int number = sp1.getInt("wordsNum",8);
            dataBase = new ArrayList<>();
            int Progress = SharedPreferencesUtils.getStudyProgress(ReciteWordsActivity.this);
            Log.d("xuedaoledijigedanci", Progress+"");
            Log.d("xuedaoledijigedanci", number+"");
            for (int i = 0; i <number ; i++) {
                  List<PhraseBean> PhraseBeans = DataSupport.where("Tag = ?", Progress+1+i+"").find(PhraseBean.class);
                  OneWordsBean dataBase2 = new OneWordsBean();
                  try{
                        Log.d("xuedaoledijigedanci", PhraseBeans.get(0).getJa());
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
                        dataBase2.setType(PhraseBeans.get(0).getType());
                        dataBase.add(dataBase2);
                  }catch (Exception e){
                        outOfBounds = false;
                        break;
                  }
            }

            if (outOfBounds){
                  int showType = dataBase.get(wordsNum).getType();
                  if (wordsNum == 0){
                        tv_word_beforeWords.setVisibility(View.GONE);
                  }else {
                        tv_word_beforeWords.setVisibility(View.VISIBLE);
                        final int wordsNumBefore = wordsNum-1;
                        tv_word_beforeWords.setText(dataBase.get(wordsNumBefore).getJa()+dataBase.get(wordsNumBefore).getCx()+dataBase.get(wordsNumBefore).getCh());
                        tv_word_beforeWords.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    Intent intent = new Intent(ReciteWordsActivity.this,BeforeActivity.class);
                                    intent.putExtra("Ja",dataBase.get(wordsNumBefore).getJa());
                                    intent.putExtra("Cx",dataBase.get(wordsNumBefore).getCx());
                                    intent.putExtra("Pic",dataBase.get(wordsNumBefore).getPic());
                                    intent.putExtra("Je",dataBase.get(wordsNumBefore).getJe());
                                    intent.putExtra("Ce",dataBase.get(wordsNumBefore).getCe());
                                    intent.putExtra("Ch",dataBase.get(wordsNumBefore).getCh());
                                    intent.putExtra("Jaudio",dataBase.get(wordsNumBefore).getJaudio());
                                    intent.putExtra("Id",dataBase.get(wordsNumBefore).getId());
                                    intent.putExtra("Jsentence",dataBase.get(wordsNumBefore).getJsentence());
                                    intent.putExtra("Jvideo",dataBase.get(wordsNumBefore).getJvideo());
                                    intent.putExtra("tag",dataBase.get(wordsNumBefore).getTag());
                                    startActivity(intent);
                              }
                        });
                  }

                  if (showType == 1){
                        rl_sentence_show.setVisibility(View.GONE);
                        //播放词的音频
                        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                              mediaPlayer.stop();
                        }else {

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
                              if (NetUtil.isNetworkAvailable(ReciteWordsActivity.this)){
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
                                    }else {
                                    Toast.makeText(ReciteWordsActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                              }
                        }
                        //显示当前需要新学单词个数
                        int need_lean = number-wordsNum;
                        tv_need_lean.setText("需新学:"+need_lean);
                        datas = random(dataBase,dataBase.size()-1,wordsNum);
                        Collections.shuffle(datas);
                        adapter = new GridViewAdapter(ReciteWordsActivity.this,handler,datas,dataBase.get(wordsNum).getJa(),wordsNum,choose);
                        gridView.setAdapter(adapter);

                        //加载页面提示信息
                        if (SharedPreferencesUtils.GetSetShow(this)){
                              String name = dataBase.get(wordsNum).getJa();
                              String jieguo = name.substring(0,name.indexOf("（"));
                              tv_words_show.setText(jieguo);
                        }else {
                              tv_words_show.setText(dataBase.get(wordsNum).getJa());
                        }

                        tv_one_japan.setText(dataBase.get(wordsNum).getJe());
                        tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
                        tv_two_name.setText(dataBase.get(wordsNum).getCh());
                        tv_two_mean.setText(dataBase.get(wordsNum).getCx());

                        rl_words_home.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    Intent intent = new Intent(ReciteWordsActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                              }
                        });
                        gv_voice.setMovieResource(R.drawable.gif1);
                        rl_voice.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {

                                          if (NetUtil.isNetworkAvailable(ReciteWordsActivity.this)){
                                                iv_voice.setVisibility(View.GONE);
                                                gv_voice.setVisibility(View.VISIBLE);

                                                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                                                  mediaPlayer.stop();
                                                            }else {

                                                            }
                                                            if (mediaPlayer != null){
                                                                  mediaPlayer.release();
                                                                  mediaPlayer = null;
                                                            }


//                                                            String path="/jinchuanxiaociMp3/1.mp3";     //这里给一个歌曲的网络地址就行了
//                                                            Uri uri  =  Uri.parse(path);
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
                                                }
                                          }else {
                                                Toast.makeText(ReciteWordsActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                                          }

                              }
                        });

                        rl_prompt.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    handler.sendEmptyMessage(0);
                              }
                        });
                  }else if (showType ==2){
                        String name = dataBase.get(wordsNum).getJa();
                        String jieguo = name.substring(name.indexOf("（")+1,name.indexOf("）"));
                        String sentence = dataBase.get(wordsNum).getJe();
                        SpannableString s = new SpannableString(sentence);
                        Pattern p = Pattern.compile(jieguo);
                        Matcher m = p.matcher(s);
                        while (m.find()) {
                              int start = m.start();
                              int end = m.end();
                              s.setSpan(new ForegroundColorSpan(Color.parseColor("#7EAEDC")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        tv_sentence_show.setText(s);
                        rl_sentence_show.setVisibility(View.VISIBLE);
                        tv_words_show.setVisibility(View.INVISIBLE);
                        //播放词的音频
                              if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                              }
                              if (mediaPlayer != null){
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                              }
                              mediaPlayer = new MediaPlayer();
                              boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+dataBase.get(wordsNum).getTag()+".mp3");
                              if (exist){
                                    try {
                                          mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+dataBase.get(wordsNum).getTag()+".mp3");
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
                                    if (NetUtil.isNetworkAvailable(ReciteWordsActivity.this)){
                                          try {
                                          mediaPlayer.setDataSource(dataBase.get(wordsNum).getJsentence());
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
                                          Toast.makeText(ReciteWordsActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                                    }

                              }

                        //显示当前需要新学单词个数
                        int need_lean = number-wordsNum;
                        tv_need_lean.setText("需新学:"+need_lean);
                        datas = random(dataBase,dataBase.size()-1,wordsNum);
                        Collections.shuffle(datas);
                        adapter = new GridViewAdapter(ReciteWordsActivity.this,handler,datas,dataBase.get(wordsNum).getJa(),wordsNum,choose);
                        gridView.setAdapter(adapter);
                        //加载页面提示信息
                        tv_words_show.setText(dataBase.get(wordsNum).getJa());
                        tv_one_japan.setText(dataBase.get(wordsNum).getJe());
                        tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
                        tv_two_name.setText(dataBase.get(wordsNum).getCh());
                        tv_two_mean.setText(dataBase.get(wordsNum).getCx());

                        rl_words_home.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    Intent intent = new Intent(ReciteWordsActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                              }
                        });
                        rl_voice.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
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
                                          if (NetUtil.isNetworkAvailable(ReciteWordsActivity.this)){
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
                                                  }else {
                                                Toast.makeText(ReciteWordsActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                                          }
                                    }
                              }
                        });
                        rl_prompt.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    handler.sendEmptyMessage(0);
                              }
                        });
                  }
                  tv_show_noWords.setVisibility(View.GONE);
            }else {
                  tv_show_noWords.setVisibility(View.VISIBLE);
                  Toast.makeText(this, "您已经学完我们目前的单词了", Toast.LENGTH_SHORT).show();
            }
      }
      Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                  switch (msg.what){
                        case 0:
                              //第一次错误显示的布局
                              if (dataBase.get(wordsNum).getType()==1){
                                    tv_one_japan.setVisibility(View.VISIBLE);
                              }else{
                                    tv_one_japan.setVisibility(View.GONE);
                              }
                              ll_one.setVisibility(View.VISIBLE);
                              ll_two.setVisibility(View.GONE);
                              ll_three.setVisibility(View.GONE);
                              ReviewBean reviewBean = new ReviewBean();
                              List<ReviewBean> data = new ArrayList<ReviewBean>();
                              data = DataSupport.where("ja= ?", dataBase.get(wordsNum).getJa()).find(ReviewBean.class);
                              if (data.size() == 0 || data == null) {
                                    reviewBean.setJa(dataBase.get(wordsNum).getJa());
                                    reviewBean.setCx(dataBase.get(wordsNum).getCx());
                                    reviewBean.setCh(dataBase.get(wordsNum).getCh());
                                    reviewBean.setPic(dataBase.get(wordsNum).getPic());
                                    reviewBean.setJe(dataBase.get(wordsNum).getJe());
                                    reviewBean.setCe(dataBase.get(wordsNum).getCe());
                                    reviewBean.setJaudio(dataBase.get(wordsNum).getJaudio());
                                    reviewBean.setJsentence(dataBase.get(wordsNum).getJsentence());
                                    reviewBean.setJvideo(dataBase.get(wordsNum).getJvideo());
                                    reviewBean.setWrongNum(1);
                                    reviewBean.setNowNuM(wordsNum);
                                    reviewBean.setTag(dataBase.get(wordsNum).getTag());
                                    reviewBean.save();
                              }else {
                                    ContentValues values = new ContentValues();
                                    values.put("wrongNum", "0");
                                    DataSupport.updateAll(ReviewBean.class, values, "ja= ?", dataBase.get(wordsNum).getJa());
                              }

                              if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                              }
                              if (mediaPlayer != null){
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                              }
                              mediaPlayer = new MediaPlayer();

                              boolean exist = fileUtils.fileIsExists(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+dataBase.get(wordsNum).getTag()+".mp3");
                              if (exist){
                                    try {
                                          mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/jinchuanxiaociMp3/"+"J"+dataBase.get(wordsNum).getTag()+".mp3");
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
                                    if (NetUtil.isNetworkAvailable(ReciteWordsActivity.this)){
                                          try {
                                                mediaPlayer.setDataSource(dataBase.get(wordsNum).getJsentence());
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
                                          Toast.makeText(ReciteWordsActivity.this, "没有网络，无法播放音频", Toast.LENGTH_SHORT).show();
                                    }
                              }
                              break;
                        case 1:
                              //第二次错误显示的布局
                              ll_one.setVisibility(View.GONE);
                              ll_two.setVisibility(View.VISIBLE);
                              ll_three.setVisibility(View.GONE);
                              break;
                        case 2:
                              //第三次错误显示的布局
                              ll_one.setVisibility(View.GONE);
                              ll_two.setVisibility(View.GONE);
                              ll_three.setVisibility(View.VISIBLE);
                              break;
                        case 3:
                              //第三次正确显示的布局
                              Intent intent = new Intent(ReciteWordsActivity.this,WordsDetailActivity.class);
                              intent.putExtra("Ja",dataBase.get(wordsNum).getJa());
                              intent.putExtra("Cx",dataBase.get(wordsNum).getCx());
                              intent.putExtra("Pic",dataBase.get(wordsNum).getPic());
                              intent.putExtra("Je",dataBase.get(wordsNum).getJe());
                              intent.putExtra("Ce",dataBase.get(wordsNum).getCe());
                              intent.putExtra("Ch",dataBase.get(wordsNum).getCh());
                              intent.putExtra("Jaudio",dataBase.get(wordsNum).getJaudio());
                              intent.putExtra("Id",dataBase.get(wordsNum).getId());
                              intent.putExtra("Jsentence",dataBase.get(wordsNum).getJsentence());
                              intent.putExtra("Jvideo",dataBase.get(wordsNum).getJvideo());
                              intent.putExtra("tag",dataBase.get(wordsNum).getTag());
                              startActivity(intent);
                              finish();
                              break;
                        case 4 :
                              SharedPreferences sp = getSharedPreferences("words", Context.MODE_PRIVATE);
                              SharedPreferences.Editor editor = sp.edit();
                              editor.putInt("judge",1);
                              editor.commit();
                              Intent intent1 = new Intent(ReciteWordsActivity.this,WordsDetailActivity.class);
                              intent1.putExtra("Ja",dataBase.get(wordsNum).getJa());
                              intent1.putExtra("Cx",dataBase.get(wordsNum).getCx());
                              intent1.putExtra("Pic",dataBase.get(wordsNum).getPic());
                              intent1.putExtra("Je",dataBase.get(wordsNum).getJe());
                              intent1.putExtra("Ce",dataBase.get(wordsNum).getCe());
                              intent1.putExtra("Ch",dataBase.get(wordsNum).getCh());
                              intent1.putExtra("Jaudio",dataBase.get(wordsNum).getJaudio());
                              intent1.putExtra("Id",dataBase.get(wordsNum).getId());
                              intent1.putExtra("Jsentence",dataBase.get(wordsNum).getJsentence());
                              intent1.putExtra("Jvideo",dataBase.get(wordsNum).getJvideo());
                              intent1.putExtra("tag",dataBase.get(wordsNum).getTag());
                              startActivity(intent1);
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
