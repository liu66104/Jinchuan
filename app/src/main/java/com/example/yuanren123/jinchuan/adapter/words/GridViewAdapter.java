package com.example.yuanren123.jinchuan.adapter.words;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.model.OneWordsBean;
import com.example.yuanren123.jinchuan.service.VideoService;
import com.squareup.picasso.Picasso;
import java.util.List;


/**
 * Created by yuanren123 on 2017/8/14.
 */

public class GridViewAdapter extends BaseAdapter {


      private Context context;
      private List<OneWordsBean> data;
      private Handler handler;
      private int wrongNum ;
      private String name;
      private int num;
      //判断是否已经错了三次的值，如果为1时，则一直跳转详细页
      private int choose;
      private boolean x;
      private boolean x1;

      public GridViewAdapter(Context context, Handler handler, List<OneWordsBean> data,String name,int num,int choose) {
            this.context = context;
            this.handler = handler;
            this.data = data;
            this.name = name;
            this.num = num;
            this.choose = choose;
            wrongNum = 0;
            x = true;
            x1 = true;

      }

      @Override
      public int getCount() {
            return data.size();
      }

      @Override
      public Object getItem(int i) {
            return data.get(i);
      }

      @Override
      public long getItemId(int i) {
            return i;
      }

      @Override
      public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder  = null;
            if (view == null){
                  viewHolder = new ViewHolder();
                  view = LayoutInflater.from(context).inflate(R.layout.item_words_picture,null);
                  viewHolder.iv = view.findViewById(R.id.iv_item_words_picture);
                  viewHolder.iv_wrong= view.findViewById(R.id.iv_item_words_wrong);
                  viewHolder.iv_correct = view.findViewById(R.id.iv_item_words_correct);
                  view.setTag(viewHolder);
            }else {

                  viewHolder = (ViewHolder) view.getTag();
            }
            final ViewHolder finalViewHolder = viewHolder;


            viewHolder.iv.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (name.equals(data.get(i).getJa())){
                              if (x){
                                    Intent intent = new Intent(context, VideoService.class);
                                    intent.putExtra("musicId", R.raw.right);
                                    context.startService(intent);
                                    finalViewHolder.iv_correct.setVisibility(View.VISIBLE);
                                    new CountDownTimer(500,1000) {
                                          //两个参数，前一个指倒计时的总时间，后一个指多长时间倒数一下。
                                          @Override
                                          public void onTick(long millisUntilFinished) {
                                                // TODO Auto-generated method stub

                                          }
                                          @Override
                                          public void onFinish() {
                                                // TODO Auto-generated method stub
                                                SharedPreferences sp = context.getSharedPreferences("StudyPlan", Context.MODE_PRIVATE);
                                                int number = sp.getInt("wordsNum",8);
                                                handler.sendEmptyMessage(3);
                                                if (num+1>number){
//
                                                }else {
                                                      num++;
                                                }
                                                SharedPreferences sp1 = context.getSharedPreferences("words", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp1.edit();
                                                editor.putInt("wordsNum", num);
                                                editor.putInt("judge",0);
                                                editor.commit();
                                                wrongNum = 0;
                                          }
                                    }.start();
                                    x = false;
                              }


                        }else {
                              Intent intent = new Intent(context, VideoService.class);
                              intent.putExtra("musicId", R.raw.wrong);
                              context.startService(intent);
                              finalViewHolder.iv_wrong.setVisibility(View.VISIBLE);
                              wrongNum ++;
                              if (choose == 1){
                                    handler.sendEmptyMessage(4);
                                    SharedPreferences sp = context.getSharedPreferences("words", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putInt("wordsNum", num);
                                    editor.commit();
                                    wrongNum = 0;
                              }else {
                                    if (wrongNum == 1){
                                          handler.sendEmptyMessage(0);
                                    }else if (wrongNum == 2){
                                          handler.sendEmptyMessage(1);
                                    }else if (wrongNum == 3){
                                          if (x1){
                                                handler.sendEmptyMessage(4);
                                                SharedPreferences sp = context.getSharedPreferences("words", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putInt("wordsNum", num);
                                                editor.commit();
                                                wrongNum = 0;
                                                x1 = false;
                                          }
                                          //handler.sendEmptyMessage(2);
                                    }else if (wrongNum == 4){

                                    }
                              }

                              new CountDownTimer(500,1000) {//两个参数，前一个指倒计时的总时间，后一个指多长时间倒数一下。

                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                          // TODO Auto-generated method stub
                                          finalViewHolder.iv_wrong.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onFinish() {
                                          // TODO Auto-generated method stub
                                          finalViewHolder.iv_wrong.setVisibility(View.GONE);


                                    }
                              }.start();
                        }

                  }
            });
            Picasso.with(context).load(data.get(i).getPic()).into(viewHolder.iv);
            return view;
      }

      class ViewHolder{

            private ImageView iv;
            private LinearLayout iv_wrong,iv_correct;
      }
}
