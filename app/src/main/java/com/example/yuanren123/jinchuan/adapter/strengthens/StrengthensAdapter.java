package com.example.yuanren123.jinchuan.adapter.strengthens;

/**
 * Created by yuanren123 on 2017/9/19.
 */


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.service.VideoService;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanren123 on 2017/8/28.
 */

public class StrengthensAdapter extends RecyclerView.Adapter<StrengthensAdapter.ViewHolder> {

      private List<ReviewBean> data;
      private Context context;
      private String Ja;
      private Handler handler;
      private int type;
      private boolean x;

      public StrengthensAdapter(Context context, List<ReviewBean> data, String Ja, Handler handler, int type) {
            this.data = data;
            this.context = context;
            this.Ja = Ja;
            this.handler  = handler;
            this.type = type;
            x  = true;
      }

      @Override
      public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_review_list, parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
      }

      @Override
      public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (type == 0){
                  holder.tv.setText(data.get(position).getCx() + data.get(position).getCh());
            }else if (type == 1){
                  holder.tv.setText(data.get(position).getJa());
            }
            holder.rl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (Ja.equals(data.get(position).getJa())) {
                              if (x){
                                    x = false;
                                    ContentValues values = new ContentValues();
                                    values.put("wrongNum", "0");
                                    DataSupport.updateAll(ReviewBean.class, values, "ja= ?", Ja);
                                    holder.iv_correct.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(context, VideoService.class);
                                    intent.putExtra("musicId", R.raw.right);
                                    context.startService(intent);
                                    new CountDownTimer(500,1000) {
                                          //两个参数，前一个指倒计时的总时间，后一个指多长时间倒数一下。
                                          @Override
                                          public void onTick(long millisUntilFinished) {
                                                // TODO Auto-generated method stub

                                          }
                                          @Override
                                          public void onFinish() {
                                                // TODO Auto-generated method stub
                                                x = true;
                                                holder.iv_correct.setVisibility(View.GONE);
                                                List<ReviewBean> data = new ArrayList<ReviewBean>();
                                                data =DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                                                if (data.size() == 0 || data == null){
                                                      showNormalDialog();
                                                }else {
                                                      handler.sendEmptyMessage(0);
                                                }
                                          }
                                    }.start();


                              }else {

                              }
                        } else {
                              if (x){
                                    x = false;
                                    holder.iv_wrong.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(context, VideoService.class);
                                    intent.putExtra("musicId", R.raw.wrong);
                                    context.startService(intent);
                                    new CountDownTimer(500,1000) {
                                          //两个参数，前一个指倒计时的总时间，后一个指多长时间倒数一下。
                                          @Override
                                          public void onTick(long millisUntilFinished) {
                                                // TODO Auto-generated method stub

                                          }
                                          @Override
                                          public void onFinish() {
                                                // TODO Auto-generated method stub
                                                x = true;
                                                holder.iv_wrong.setVisibility(View.GONE);
                                                handler.sendEmptyMessage(1);
                                          }
                                    }.start();
                                    }else {

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


      private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
            final AlertDialog.Builder normalDialog =
                      new AlertDialog.Builder(context);
            normalDialog.setTitle("");
            normalDialog.setMessage("恭喜您已经全部复习完毕");
            normalDialog.setPositiveButton("确定",
                      new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                  //...To-do
                                  SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                  SharedPreferences.Editor editor = sp.edit();
                                  editor.putInt("review", 0);
                                  editor.commit();
                                  handler.sendEmptyMessage(8);
                            }
                      });
            // 显示
            normalDialog.setCancelable(false);
            normalDialog.show();
      }
}
