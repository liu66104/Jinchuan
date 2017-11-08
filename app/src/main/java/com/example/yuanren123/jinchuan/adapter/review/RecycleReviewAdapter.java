package com.example.yuanren123.jinchuan.adapter.review;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.strengthens.StrengthensActivity;
import com.example.yuanren123.jinchuan.model.OneWordsBean;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.service.VideoService;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanren123 on 2017/8/28.
 */

public class RecycleReviewAdapter extends RecyclerView.Adapter<RecycleReviewAdapter.ViewHolder> {

      private List<OneWordsBean> data;
      private Context context;
      private String Ja;
      private int num;
      private Handler handler;
      private int type;
      private boolean x;
      private int wrongNum;

      public RecycleReviewAdapter(Context context, List<OneWordsBean> data, String Ja, int num, Handler handler,int type) {
            this.data = data;
            this.context = context;
            this.Ja = Ja;
            this.num = num;
            this.handler  = handler;
            this.type = type;
            x  = true;
            wrongNum = 0;
      }

      @Override
      public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_review_list,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
      }

      @Override
      public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (type == 0){
                  holder.tv.setText(data.get(position).getCx() + data.get(position).getCh());
            }else if (type == 1){

                  if (SharedPreferencesUtils.GetSetShow(context)){
                        String name = data.get(position).getJa();
                        String jieguo = name.substring(0,name.indexOf("（"));
                        holder.tv.setText(jieguo);
                  }else {
                        holder.tv.setText(data.get(position).getJa());
                  }

            }
            holder.rl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (Ja.equals(data.get(position).getJa())) {

                              if(x){
                                    x = false;
                                          if (SharedPreferencesUtils.Choose(context)){
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
                                                            List<ReviewBean> reviewBean = DataSupport.findAll(ReviewBean.class);
                                                            if (num + 2 > reviewBean.size()) {
                                                                  List<ReviewBean> data = new ArrayList<ReviewBean>();
                                                                  data =DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                                                                  if (data.size() == 0 || data == null){
                                                                        num = 0;
                                                                        SharedPreferencesUtils.ChooseResult(context,false);
                                                                        SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                                        SharedPreferences.Editor editor = sp.edit();
                                                                        editor.putInt("review", num);
                                                                        editor.commit();
                                                                        showNormalDialog();
                                                                  }else {
                                                                        Intent intent = new Intent(context,StrengthensActivity.class);
                                                                        context.startActivity(intent);
                                                                        handler.sendEmptyMessage(8);
                                                                  }
                                                            } else {
                                                                  num++;
                                                                  SharedPreferencesUtils.ChooseResult(context,false);
                                                                  SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                                  SharedPreferences.Editor editor = sp.edit();
                                                                  editor.putInt("review", num);
                                                                  editor.commit();
                                                                  handler.sendEmptyMessage(0);
                                                                  x = true;
                                                            }

                                                      }
                                                }.start();

                                          }else {
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
                                                            List<ReviewBean> reviewBean = DataSupport.findAll(ReviewBean.class);
                                                            if (num + 2 > reviewBean.size()) {
                                                                  List<ReviewBean> data = new ArrayList<ReviewBean>();
                                                                  data =DataSupport.where("wrongNum= ?", 1+"").find(ReviewBean.class);
                                                                  if (data.size() == 0 || data == null){
                                                                        num = 0;
                                                                        SharedPreferencesUtils.ChooseResult(context,false);
                                                                        SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                                        SharedPreferences.Editor editor = sp.edit();
                                                                        editor.putInt("review", num);
                                                                        editor.commit();
                                                                        showNormalDialog();
                                                                  }else {
                                                                        Intent intent = new Intent(context, StrengthensActivity.class);
                                                                        context.startActivity(intent);
                                                                        handler.sendEmptyMessage(8);
                                                                  }
                                                            } else {
                                                                  num++;
                                                                  SharedPreferencesUtils.ChooseResult(context,false);
                                                                  SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                                  SharedPreferences.Editor editor = sp.edit();
                                                                  editor.putInt("review", num);
                                                                  editor.commit();
                                                                  handler.sendEmptyMessage(1);
                                                                  x = true;
                                                            }

                                                      }
                                                }.start();
                                          }
                              }
                        } else {
                              if (x){
                                    x = false;
                                    if (SharedPreferencesUtils.Choose(context)){
                                          wrongNum =0;
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
                                                      holder.iv_wrong.setVisibility(View.GONE);
                                                      SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                      SharedPreferences.Editor editor = sp.edit();
                                                      editor.putInt("review", num);
                                                      editor.commit();
                                                      handler.sendEmptyMessage(0);
                                                      x = true;
                                                }
                                          }.start();

                                    }else {
                                          if (wrongNum == 0){
                                                wrongNum++;
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
                                                            holder.iv_wrong.setVisibility(View.GONE);
                                                            SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sp.edit();
                                                            editor.putInt("review", num);
                                                            editor.commit();
                                                            handler.sendEmptyMessage(2);
                                                            x = true;
                                                      }
                                                }.start();
                                          }else if (wrongNum ==1){
                                                wrongNum++;
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
                                                            holder.iv_wrong.setVisibility(View.GONE);
                                                            SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sp.edit();
                                                            editor.putInt("review", num);
                                                            editor.commit();
                                                            handler.sendEmptyMessage(3);
                                                            x = true;
                                                      }
                                                }.start();
                                          }else if (wrongNum == 2){
                                                wrongNum =0;
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
                                                            SharedPreferencesUtils.ChooseResult(context,true);
                                                            holder.iv_wrong.setVisibility(View.GONE);
                                                            SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sp.edit();
                                                            editor.putInt("review", num);
                                                            editor.commit();
                                                            handler.sendEmptyMessage(0);
                                                            x = true;
                                                      }
                                                }.start();
                                          }
                                    }
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
                                  handler.sendEmptyMessage(8);
                            }
                      });
            // 显示
            normalDialog.setCancelable(false);
            normalDialog.show();
      }
}
