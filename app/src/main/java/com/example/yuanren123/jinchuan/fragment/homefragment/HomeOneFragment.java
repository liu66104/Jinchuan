package com.example.yuanren123.jinchuan.fragment.homefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.plan.PlanActivity;
import com.example.yuanren123.jinchuan.base.BaseFragment;
import com.example.yuanren123.jinchuan.model.DateBean;
import com.example.yuanren123.jinchuan.model.ReviewBean;

import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by yuanren123 on 2017/7/31.
 */
@ContentView(R.layout.fragment_home_one)
public class HomeOneFragment extends BaseFragment {

      @ViewInject(R.id.tv_frag_day)
      private TextView tv_day;
      @ViewInject(R.id.tv_frag_words)
      private TextView tv_words;
      @ViewInject(R.id.tv_choose_book)
      private TextView tv_choose_book;
      @ViewInject(R.id.rl_frag_plan)
      private RelativeLayout rl_plan;
      @ViewInject(R.id.rl_frag_noPlan)
      private RelativeLayout rl_noPlan;
      @ViewInject(R.id.btn_frag_plan)
      private Button btn_plan;
      @ViewInject(R.id.progressBar)
      private ProgressBar progressBar;
      @ViewInject(R.id.tv_frag_one)
      private TextView tv_progress;

      private LocalBroadcastManager broadcastManager;
      private IntentFilter intentFilter;
      private BroadcastReceiver mReceiver;

      @Override
      public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            SharedPreferences sp = getActivity().getSharedPreferences("Plan", Context.MODE_PRIVATE);
            String day = sp.getString("day","");

            List<ReviewBean> reviewBean = DataSupport.findAll(ReviewBean.class);

            int progress = reviewBean.size()*100/925;
            Log.d("weadsadwaew", progress+"!!!!"+ reviewBean.size());
            progressBar.setProgress(progress);
            tv_progress.setText("当前进度 : "+reviewBean.size()+"/925");

            if (day.equals("")||day == null){
                  rl_plan.setVisibility(View.GONE);
                  rl_noPlan.setVisibility(View.VISIBLE);
                  btn_plan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                              Intent intent = new Intent(getActivity(), PlanActivity.class);
                              startActivity(intent);
                        }
                  });
            }else {
                  rl_plan.setVisibility(View.VISIBLE);
                  rl_noPlan.setVisibility(View.GONE);
                  String num = sp.getString("wordsNum","");
                  int bookType = sp.getInt("BookType",0);
                  List<DateBean> dateBeans = DataSupport.findAll(DateBean.class);
                  int days =Integer.parseInt(day.substring(0,day.indexOf("天"))) - dateBeans.size() ;
                  tv_day.setText(days+"");
                  tv_words.setText(num.substring(0,num.indexOf("个")));
                  switch (bookType){
                        case 1:
                              tv_choose_book.setText("标准日本语初级上册");
                              break;
                        case 2:
                              tv_choose_book.setText("标准日本语中级");
                              break;
                        case 3:
                              tv_choose_book.setText("标准日本语高级");
                              break;
                        default:
                              tv_choose_book.setText("还没有制定计划");
                              break;
                  }
            }
            broadcastManager = LocalBroadcastManager.getInstance(getActivity());
            intentFilter = new IntentFilter();
            intentFilter.addAction("plan");
            mReceiver = new BroadcastReceiver() {
                  @Override
                  public void onReceive(Context context, Intent intent) {
                        rl_plan.setVisibility(View.VISIBLE);
                        rl_noPlan.setVisibility(View.GONE);
                        Log.d("dasdwdasd", "收到");
                        SharedPreferences sp = getActivity().getSharedPreferences("Plan", Context.MODE_PRIVATE);
                        String day = sp.getString("day","");
                        String num = sp.getString("wordsNum","");
                        int bookType = sp.getInt("BookType",0);
                        tv_day.setText(day.substring(0,day.indexOf("天")));
                        tv_words.setText(num.substring(0,num.indexOf("个")));
                        switch (bookType){
                              case 1:
                                    tv_choose_book.setText("标准日本语初级");
                                    break;
                              case 2:
                                    tv_choose_book.setText("标准日本语中级");
                                    break;
                              case 3:
                                    tv_choose_book.setText("标准日本语高级");
                                    break;
                              default:
                                    tv_choose_book.setText("还没有制定计划");
                                    break;
                        }

                  }
            };
            broadcastManager.registerReceiver(mReceiver, intentFilter);
      }
}
