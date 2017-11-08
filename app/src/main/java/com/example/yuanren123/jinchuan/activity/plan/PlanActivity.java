package com.example.yuanren123.jinchuan.activity.plan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import com.example.yuanren123.jinchuan.view.MyRadioGroup;
import com.example.yuanren123.jinchuan.view.wheel.widget.OnWheelChangedListener;
import com.example.yuanren123.jinchuan.view.wheel.widget.WheelView;
import com.example.yuanren123.jinchuan.view.wheel.widget.adapters.ArrayWheelAdapter;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by yuanren123 on 2017/8/9.
 */
@ContentView(R.layout.activity_plan)
public class PlanActivity extends BaseActivity implements OnWheelChangedListener{

      @ViewInject(R.id.wv_day)
      private WheelView wv_day;
      @ViewInject(R.id.wv_number)
      private WheelView wv_number;
      @ViewInject(R.id.plan_rg)
      private MyRadioGroup rg;
      @ViewInject(R.id.tv_plan)
      private TextView tv_plan_sub;
      @ViewInject(R.id.plan_iv_back)
      private ImageView iv_back;

      private String[] dayArray;
      private String[] numArray;

      private int BookType;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            BookType = 1;
            dayArray = new String[5];
            dayArray[0] = 50+"天";
            dayArray[1] = 100+"天";
            dayArray[2] = 140+"天";
            dayArray[3] = 160+"天";
            dayArray[4] = 200+"天";

            numArray = new String[5];
            numArray[4] = 20+"个单词";
            numArray[3] = 10+"个单词";
            numArray[2] = 7+"个单词";
            numArray[1] = 6+"个单词";
            numArray[0] = 5+"个单词";


            wv_day.setViewAdapter(new ArrayWheelAdapter<String>(
                      PlanActivity.this, dayArray));

            wv_number.setViewAdapter(new ArrayWheelAdapter<String>(
                      PlanActivity.this,numArray
            ));

            // 默认显示第一项
            wv_day.setCurrentItem(0);
            // 默认显示第一项
            wv_number.setCurrentItem(4);
            // 默认显示第一项

            // 页面上显示7项
            wv_number.setVisibleItems(3);
            wv_day.setVisibleItems(3);


            // 添加滑动事件
            wv_number.addChangingListener(PlanActivity.this);
            wv_day.addChangingListener(PlanActivity.this);

            rg.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                                                      switch (checkedId) {
                                                            case R.id.rbt_one:
                                                                  dayArray = new String[5];
                                                                  dayArray[0] = 50+"天";
                                                                  dayArray[1] = 100+"天";
                                                                  dayArray[2] = 140+"天";
                                                                  dayArray[3] = 160+"天";
                                                                  dayArray[4] = 200+"天";
                                                                  numArray = new String[5];
                                                                  numArray[4] = 20+"个单词";
                                                                  numArray[3] = 10+"个单词";
                                                                  numArray[2] = 7+"个单词";
                                                                  numArray[1] = 6+"个单词";
                                                                  numArray[0] = 5+"个单词";
                                                                  wv_day.setViewAdapter(new ArrayWheelAdapter<String>(
                                                                            PlanActivity.this, dayArray));

                                                                  wv_number.setViewAdapter(new ArrayWheelAdapter<String>(
                                                                            PlanActivity.this,numArray
                                                                  ));
                                                                  // 默认显示第一项
                                                                  wv_day.setCurrentItem(0);
                                                                  // 默认显示第一项
                                                                  wv_number.setCurrentItem(4);
                                                                  // 默认显示第一项

                                                                  // 页面上显示7项
                                                                  wv_number.setVisibleItems(3);
                                                                  wv_day.setVisibleItems(3);
                                                                  BookType = 1;
                                                                  break;
//                                                            case R.id.rbt_two:
//
//                                                                  dayArray = new String[20];
//                                                                  for (int i = 0; i <20 ; i++) {
//                                                                        dayArray[i] = (i+3)*3+"";
//                                                                  }
//                                                                  numArray = new String[20];
//                                                                  for (int i = 0; i <20 ; i++) {
//                                                                        int a = Integer.parseInt(dayArray[i]);
//                                                                        int b =  6000/a;
//                                                                        numArray[19-i] = b+"个单词";
//                                                                  }
//
//                                                                  wv_day.setViewAdapter(new ArrayWheelAdapter<String>(
//                                                                            PlanActivity.this, dayArray));
//
//                                                                  wv_number.setViewAdapter(new ArrayWheelAdapter<String>(
//                                                                            PlanActivity.this,numArray
//                                                                  ));
//
//                                                                  // 默认显示第一项
//                                                                  wv_day.setCurrentItem(0);
//                                                                  // 默认显示第一项
//                                                                  wv_number.setCurrentItem(19);
//                                                                  // 默认显示第一项
//
//                                                                  // 页面上显示7项
//                                                                  wv_number.setVisibleItems(10);
//                                                                  wv_day.setVisibleItems(10);
//
//                                                                  // 添加滑动事件
//                                                                  wv_number.addChangingListener(PlanActivity.this);
//                                                                  wv_day.addChangingListener(PlanActivity.this);
//                                                                  BookType = 2;
//
//                                                                  break;
//                                                            case R.id.rbt_three:
//                                                                  dayArray = new String[20];
//                                                                  for (int i = 0; i <20 ; i++) {
//                                                                        dayArray[i] = (i+3)*3+"";
//                                                                  }
//                                                                  numArray = new String[20];
//                                                                  for (int i = 0; i <20 ; i++) {
//                                                                        int a = Integer.parseInt(dayArray[i]);
//                                                                        int b =  8000/a;
//                                                                        numArray[19-i] = b+"个单词";
//                                                                  }
//
//                                                                  wv_day.setViewAdapter(new ArrayWheelAdapter<String>(
//                                                                            PlanActivity.this, dayArray));
//
//                                                                  wv_number.setViewAdapter(new ArrayWheelAdapter<String>(
//                                                                            PlanActivity.this,numArray
//                                                                  ));
//
//                                                                  // 默认显示第一项
//                                                                  wv_day.setCurrentItem(0);
//                                                                  // 默认显示第一项
//                                                                  wv_number.setCurrentItem(19);
//                                                                  // 默认显示第一项
//
//                                                                  // 页面上显示7项
//                                                                  wv_number.setVisibleItems(10);
//                                                                  wv_day.setVisibleItems(10);
//
//                                                                  // 添加滑动事件
//                                                                  wv_number.addChangingListener(PlanActivity.this);
//                                                                  wv_day.addChangingListener(PlanActivity.this);
//                                                                  BookType = 3;
//                                                                  break;
                                                      }
                                                }
                                          }
            );
            tv_plan_sub.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Log.d("dasdwdasd", "点了");
                        String day = dayArray[wv_day.getCurrentItem()];
                        String wordsNum = numArray[wv_number.getCurrentItem()];
                        SharedPreferencesUtils.savePlan(PlanActivity.this,BookType,day,wordsNum);
                        //存入每天学单词个数，words页面取词用
                        int number = Integer.parseInt(wordsNum.substring(0,wordsNum.indexOf("个")));
                        SharedPreferencesUtils.saveStudyPlan(PlanActivity.this,number);
//                        switch (BookType){
//                              case 1:
//                                    sendBroadcast();
//                                    Log.d("dasdwdasd", "发送1");
//                                    Toast.makeText(PlanActivity.this, "制定了初日的计划"+day+"天"+wordsNum, Toast.LENGTH_LONG).show();
//                                    break;
//                              case 2:
//                                    sendBroadcast();
//                                    Log.d("dasdwdasd", "发送1");
//                                    Toast.makeText(PlanActivity.this, "制定了中日的计划"+day+"天"+wordsNum, Toast.LENGTH_LONG).show();
//                                    break;
//                              case 3:
//                                    sendBroadcast();
//                                    Log.d("dasdwdasd", "发送1");
//                                    Toast.makeText(PlanActivity.this, "制定了高日的计划"+day+"天"+wordsNum, Toast.LENGTH_LONG).show();
//                                    break;
//                              default:
//                                    break;
//
//                        }
                        sendBroadcast();
                        finish();


                  }
            });
            iv_back.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        finish();
                  }
            });
      }
      public void sendBroadcast() {
            Intent intent = new Intent("plan");
            Log.d("dasdwdasd", "发送");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

      }


      @Override
      public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (wheel == wv_day) {
                  // 更新省的时候不仅要更新市同时也要更新区县
                  upNumber();

            } else if (wheel == wv_number) {
                  // 更新市的时候只用更新区县即可
                  upDay();
            }
      }

      private void upDay() {
            int day = wv_number.getCurrentItem();
            wv_day.setCurrentItem(4- day);
      }

      private void upNumber() {
            int day = wv_day.getCurrentItem();
            wv_number.setCurrentItem(4 - day);
      }


      @Override
      public void initView() {

      }

      @Override
      public void initData() {

      }

      @Override
      public int setLayout() {
            return R.layout.activity_plan;
      }
}
