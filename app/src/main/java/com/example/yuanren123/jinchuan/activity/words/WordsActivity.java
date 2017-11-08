//package com.example.yuanren123.jinchuan.activity.words;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.yuanren123.jinchuan.MainActivity;
//import com.example.yuanren123.jinchuan.R;
//import com.example.yuanren123.jinchuan.adapter.words.GridViewAdapter;
//import com.example.yuanren123.jinchuan.base.BaseActivity;
//import com.example.yuanren123.jinchuan.model.OneWordsBean;
//import com.example.yuanren123.jinchuan.model.PhraseBean;
//import com.example.yuanren123.jinchuan.model.ReviewBean;
//import com.example.yuanren123.jinchuan.service.VideoService;
//import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
//import com.example.yuanren123.jinchuan.view.MyGifView;
//
//import org.litepal.crud.DataSupport;
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.ViewInject;
//import org.xutils.x;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by yuanren123 on 2017/8/14.
// */
//@ContentView(R.layout.activity_words)
//public class WordsActivity extends BaseActivity {
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
//      private RelativeLayout rl_prompt;
//   //   private WordsBean data;
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
//      private TextView tv_show_noWords;
//      @ViewInject(R.id.iv_word_voice)
//      private ImageView iv_voice;
//      @ViewInject(R.id.gv_word_voice)
//      private MyGifView gv_voice;
//
//
//      private int wordsNum;
//      private int choose;
//      private MediaPlayer mediaPlayer;
//      private Boolean video;
//      private List<OneWordsBean>  datas;
//      private List<OneWordsBean> dataBase;
//      private Boolean outOfBounds = true;
//      private GridViewAdapter adapter;
//
//      @Override
//      protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            x.view().inject(this);
//
//            //取学到了第几个单词
//            SharedPreferences sp = getSharedPreferences("words", Context.MODE_PRIVATE);
//            wordsNum  = sp.getInt("wordsNum", 0);
//            choose = sp.getInt("judge",0);
//            List<ReviewBean> reviewBean = DataSupport.findAll(ReviewBean.class);
//            tv_need_review.setText("需复习:"+reviewBean.size());
//            //取计划学几个单词
//            SharedPreferences sp1 = getSharedPreferences("StudyPlan", Context.MODE_PRIVATE);
//            int number = sp1.getInt("wordsNum",8);
//            int need_lean = number-wordsNum;
//
//
//            video = true;
//
//
//            dataBase = new ArrayList<>();
//
//            int Progress = SharedPreferencesUtils.getStudyProgress(WordsActivity.this);
//
//            Log.d("xuedaoledijigedanci", Progress+"");
//            Log.d("xuedaoledijigedanci", number+"");
//
//
//            for (int i = 0; i <number ; i++) {
//                  List<PhraseBean> PhraseBeans = DataSupport.where("Tag = ?", Progress+1+i+"").find(PhraseBean.class);
//                  OneWordsBean dataBase2 = new OneWordsBean();
//                  try{
//
//                        dataBase2.setJa(PhraseBeans.get(0).getJa());
//                        dataBase2.setCx(PhraseBeans.get(0).getCx());
//                        dataBase2.setCh(PhraseBeans.get(0).getCh());
//                        dataBase2.setPic(PhraseBeans.get(0).getPic());
//                        dataBase2.setJe(PhraseBeans.get(0).getJe());
//                        dataBase2.setCe(PhraseBeans.get(0).getCe());
//                        dataBase2.setJaudio(PhraseBeans.get(0).getJaudio());
//                        dataBase2.setJsentence(PhraseBeans.get(0).getJsentence());
//                        dataBase2.setJvideo(PhraseBeans.get(0).getJvideo());
//                        dataBase2.setId(PhraseBeans.get(0).getId());
//                        dataBase2.setTag(PhraseBeans.get(0).getTag());
//                        dataBase.add(dataBase2);
//
//
//                  }catch (Exception e){
//
//                        outOfBounds = false;
//                        break;
//                  }
//
//            }
//
//
//
//
//            if (outOfBounds){
//                  String path=dataBase.get(wordsNum).getJaudio();     //这里给一个歌曲的网络地址就行了
//                  Intent intent = new Intent(this, VideoService.class);
//                  intent.putExtra("url", path);
//                  startService(intent);
//                  tv_need_lean.setText("需新学:"+need_lean);
//                  datas = random(dataBase,dataBase.size()-1,wordsNum);
//                  Collections.shuffle(datas);
//                  adapter = new GridViewAdapter(WordsActivity.this,handler,datas,dataBase.get(wordsNum).getJa(),wordsNum,choose);
//                  gridView.setAdapter(adapter);
//                  tv_words_show.setText(dataBase.get(wordsNum).getJa());
//                  tv_one_japan.setText(dataBase.get(wordsNum).getJe());
//                  tv_one_Chinese.setText(dataBase.get(wordsNum).getCe());
//                  tv_two_name.setText(dataBase.get(wordsNum).getCh());
//                  tv_two_mean.setText(dataBase.get(wordsNum).getCx());
////                  rl_words_detail.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////                              Intent intent = new Intent(WordsActivity.this,WordsDetailActivity.class);
////                              startActivity(intent);
////                              finish();
////                        }
////                  });
//                  rl_words_home.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                              Intent intent = new Intent(WordsActivity.this,MainActivity.class);
//                              startActivity(intent);
//                              finish();
//                        }
//                  });
//
//
//
//                  rl_voice.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                              if (video){
//
//                                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                          mediaPlayer.stop();
//                                    }
//                                    if (mediaPlayer != null){
//                                          mediaPlayer.release();
//                                          mediaPlayer = null;
//                                    }
//                                    gv_voice.setMovieResource(R.drawable.gif1);
//                                    iv_voice.setVisibility(View.GONE);
//                                    gv_voice.setVisibility(View.VISIBLE);
//                                    video = false;
//                                    String path=dataBase.get(wordsNum).getJaudio();     //这里给一个歌曲的网络地址就行了
//                                    Uri uri  =  Uri.parse(path);
//                                    mediaPlayer  =MediaPlayer.create(WordsActivity.this,uri);
//                                    mediaPlayer.start();
//
//                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                          @Override
//                                          public void onCompletion(MediaPlayer mediaPlayer) {
//                                                iv_voice.setVisibility(View.VISIBLE);
//                                                gv_voice.setVisibility(View.GONE);
//                                                video = true;
//                                          }
//                                    });
//
//                              }else {
//
//                              }
//                        }
//                  });
//
//                  rl_prompt.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                              handler.sendEmptyMessage(0);
//                        }
//                  });
//
//
//
//            }else {
//                  tv_show_noWords.setVisibility(View.VISIBLE);
//                  ll_main.setVisibility(View.GONE);
//                  rl_main.setVisibility(View.GONE);
//                  gridView.setVisibility(View.GONE);
//                  rl_prompt.setVisibility(View.GONE);
//                  Toast.makeText(this, "您已经学完我们目前的单词了", Toast.LENGTH_SHORT).show();
//            }
//
//
//
//
//
//      }
//      Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                  switch (msg.what){
//                        case 0:
//                              //第一次错误显示的布局
//                              ll_one.setVisibility(View.VISIBLE);
//                              ll_two.setVisibility(View.GONE);
//                              ll_three.setVisibility(View.GONE);
//                              ReviewBean reviewBean = new ReviewBean();
//                              List<ReviewBean> data = new ArrayList<ReviewBean>();
//                              data = DataSupport.where("ja= ?", dataBase.get(wordsNum).getJa()).find(ReviewBean.class);
//                              if (data.size() == 0 || data == null) {
//                                    reviewBean.setJa(dataBase.get(wordsNum).getJa());
//                                    reviewBean.setCx(dataBase.get(wordsNum).getCx());
//                                    reviewBean.setCh(dataBase.get(wordsNum).getCh());
//                                    reviewBean.setPic(dataBase.get(wordsNum).getPic());
//                                    reviewBean.setJe(dataBase.get(wordsNum).getJe());
//                                    reviewBean.setCe(dataBase.get(wordsNum).getCe());
//                                    reviewBean.setJaudio(dataBase.get(wordsNum).getJaudio());
//                                    reviewBean.setJsentence(dataBase.get(wordsNum).getJsentence());
//                                    reviewBean.setJvideo(dataBase.get(wordsNum).getJvideo());
//                                    reviewBean.setWrongNum(1);
//                                    reviewBean.setNowNuM(wordsNum);
//                                    reviewBean.save();
//                              }
//                              if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                                    mediaPlayer.stop();
//                              }
//                              if (mediaPlayer != null){
//                                    mediaPlayer.release();
//                                    mediaPlayer = null;
//                              }
//                              String path=dataBase.get(wordsNum).getJsentence();     //这里给一个歌曲的网络地址就行了
//                              Uri uri  =  Uri.parse(path);
//                              mediaPlayer  =MediaPlayer.create(WordsActivity.this,uri);
//                              mediaPlayer.start();
//
//
//                              break;
//                        case 1:
//                              //第二次错误显示的布局
//                              ll_one.setVisibility(View.GONE);
//                              ll_two.setVisibility(View.VISIBLE);
//                              ll_three.setVisibility(View.GONE);
//                              break;
//                        case 2:
//                              //第三次错误显示的布局
//                              ll_one.setVisibility(View.GONE);
//                              ll_two.setVisibility(View.GONE);
//                              ll_three.setVisibility(View.VISIBLE);
//                              break;
//                        case 3:
//                              //第三次正确显示的布局
//                              Intent intent = new Intent(WordsActivity.this,WordsDetailActivity.class);
//                              intent.putExtra("Ja",dataBase.get(wordsNum).getJa());
//                              intent.putExtra("Cx",dataBase.get(wordsNum).getCx());
//                              intent.putExtra("Pic",dataBase.get(wordsNum).getPic());
//                              intent.putExtra("Je",dataBase.get(wordsNum).getJe());
//                              intent.putExtra("Ce",dataBase.get(wordsNum).getCe());
//                              intent.putExtra("Ch",dataBase.get(wordsNum).getCh());
//                              intent.putExtra("Jaudio",dataBase.get(wordsNum).getJaudio());
//                              intent.putExtra("Id",dataBase.get(wordsNum).getId());
//                              intent.putExtra("Jsentence",dataBase.get(wordsNum).getJsentence());
//                              intent.putExtra("Jvideo",dataBase.get(wordsNum).getJvideo());
//                              intent.putExtra("tag",dataBase.get(wordsNum).getTag());
//                              startActivity(intent);
//                              finish();
//                              break;
//                        case 4 :
//                              SharedPreferences sp = getSharedPreferences("words", Context.MODE_PRIVATE);
//                              SharedPreferences.Editor editor = sp.edit();
//                              editor.putInt("judge",1);
//                              editor.commit();
//                              Intent intent1 = new Intent(WordsActivity.this,WordsDetailActivity.class);
//                              intent1.putExtra("Ja",dataBase.get(wordsNum).getJa());
//                              intent1.putExtra("Cx",dataBase.get(wordsNum).getCx());
//                              intent1.putExtra("Pic",dataBase.get(wordsNum).getPic());
//                              intent1.putExtra("Je",dataBase.get(wordsNum).getJe());
//                              intent1.putExtra("Ce",dataBase.get(wordsNum).getCe());
//                              intent1.putExtra("Ch",dataBase.get(wordsNum).getCh());
//                              intent1.putExtra("Jaudio",dataBase.get(wordsNum).getJaudio());
//                              intent1.putExtra("Id",dataBase.get(wordsNum).getId());
//                              intent1.putExtra("Jsentence",dataBase.get(wordsNum).getJsentence());
//                              intent1.putExtra("Jvideo",dataBase.get(wordsNum).getJvideo());
//                              intent1.putExtra("tag",dataBase.get(wordsNum).getTag());
//                              startActivity(intent1);
//                              finish();
//                        default:
//                              break;
//
//                  }
//                  super.handleMessage(msg);
//            }
//      };
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
//      protected void onDestroy() {
//            super.onDestroy();
//            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                  mediaPlayer.stop();
//            }
//            if (mediaPlayer != null){
//                  mediaPlayer.release();
//                  mediaPlayer = null;
//            }
//
//      }
//
//      @Override
//      public int setLayout() {
//            return R.layout.activity_words;
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
//                result.add(dataBase.get(list.get(i)));
//            }
//            result.add(dataBase.get(NowNum));
//            return result;
//      }
//
//
//
//}
