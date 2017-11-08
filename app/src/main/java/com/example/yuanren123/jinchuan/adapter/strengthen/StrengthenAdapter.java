package com.example.yuanren123.jinchuan.adapter.strengthen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.model.OneWordsBean;
import com.example.yuanren123.jinchuan.service.VideoService;

import java.util.List;

/**
 * Created by yuanren123 on 2017/9/14.
 */

public class StrengthenAdapter extends RecyclerView.Adapter<StrengthenAdapter.ViewHolder> {

      private List<OneWordsBean> data;
      private Context context;
      private String Ja;
      private Handler handler;
      private int type;
      private boolean x;

      public StrengthenAdapter(Context context, List<OneWordsBean> data, String Ja, Handler handler) {
            this.data = data;
            this.context = context;
            this.Ja = Ja;
            this.handler = handler;
            x = true;
      }

      @Override
      public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_review_list, parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
      }

      @Override
      public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.tv.setText(data.get(position).getCx() + data.get(position).getCh());

            holder.rl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (Ja.equals(data.get(position).getJa())) {
                              if (x){
                                    x = false;
                                    Intent intent = new Intent(context, VideoService.class);
                                    intent.putExtra("musicId", R.raw.right);
                                    context.startService(intent);
                                    holder.iv_correct.setVisibility(View.VISIBLE);
                                    new CountDownTimer(500,1000) {
                                          //两个参数，前一个指倒计时的总时间，后一个指多长时间倒数一下。
                                          @Override
                                          public void onTick(long millisUntilFinished) {
                                                // TODO Auto-generated method stub

                                          }
                                          @Override
                                          public void onFinish() {
                                                // TODO Auto-generated method stub
                                                handler.sendEmptyMessage(0);
                                                x = true;
                                          }
                                    }.start();


                              }
                        } else {
                              if (x){
                                    x = false;
                                    Intent intent = new Intent(context, VideoService.class);
                                    intent.putExtra("musicId", R.raw.wrong);
                                    context.startService(intent);
                                    holder.iv_wrong.setVisibility(View.VISIBLE);
                                    new CountDownTimer(500,1000) {
                                          //两个参数，前一个指倒计时的总时间，后一个指多长时间倒数一下。
                                          @Override
                                          public void onTick(long millisUntilFinished) {
                                                // TODO Auto-generated method stub

                                          }
                                          @Override
                                          public void onFinish() {
                                                // TODO Auto-generated method stub
                                                handler.sendEmptyMessage(1);
                                                x = true;
                                          }
                                    }.start();
                              }
                        }
                  }

            });

      }

      @Override
      public int getItemCount() {
            return data.size();
      }

      public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv;
            private RelativeLayout rl;
            private LinearLayout iv_wrong,iv_correct;

            public ViewHolder(View itemView) {
                  super(itemView);
                  tv = itemView.findViewById(R.id.tv_review_mean);
                  rl = itemView.findViewById(R.id.rl_review_item);
                  iv_wrong= itemView.findViewById(R.id.iv_item_words_wrong);
                  iv_correct = itemView.findViewById(R.id.iv_item_words_correct);

            }
      }
}