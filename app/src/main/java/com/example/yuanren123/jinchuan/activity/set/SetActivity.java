package com.example.yuanren123.jinchuan.activity.set;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by yuanren123 on 2017/9/27.
 */
@ContentView(R.layout.activity_set)
public class SetActivity extends BaseActivity {

      @ViewInject(R.id.recite_iv_back)
      private ImageView iv_back;
      @ViewInject(R.id.switch1)
      private Switch sw1;
      @ViewInject(R.id.switch2)
      private Switch sw2;
      @ViewInject(R.id.switch3)
      private Switch sw3;
      @ViewInject(R.id.switch4)
      private Switch sw4;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            iv_back.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        finish();
                  }
            });
            if (SharedPreferencesUtils.GetSetShow(this)){
                  sw1.setChecked(true);
            }else {
                  sw1.setChecked(false);
            }
            if (SharedPreferencesUtils.GetSetType1(this)){
                  sw2.setChecked(true);
            }else {
                  sw2.setChecked(false);
            }
            if (SharedPreferencesUtils.GetSetType2(this)){
                  sw3.setChecked(true);
            }else {
                  sw3.setChecked(false);
            }
            if (SharedPreferencesUtils.GetSetType3(this)){
                  sw4.setChecked(true);
            }else {
                  sw4.setChecked(false);
            }
            sw1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (sw1.isChecked()){
                              SharedPreferencesUtils.SetShow(SetActivity.this,true);
                        }else {
                              SharedPreferencesUtils.SetShow(SetActivity.this,false);
                        }
                  }
            });
            sw2.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (sw2.isChecked()){
                              SharedPreferencesUtils.SetType1(SetActivity.this,true);
                        }else {
                              SharedPreferencesUtils.SetType1(SetActivity.this,false);
                        }
                  }
            });
            sw3.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (sw3.isChecked()){
                              SharedPreferencesUtils.SetType2(SetActivity.this,true);
                        }else {
                              SharedPreferencesUtils.SetType2(SetActivity.this,false);
                        }
                  }
            });
            sw4.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        if (sw4.isChecked()){
                              SharedPreferencesUtils.SetType3(SetActivity.this,true);
                        }else {
                              SharedPreferencesUtils.SetType3(SetActivity.this,false);
                        }
                  }
            });


      }
//      public void swtich1Click1(View view){
//            Switch sw = (Switch)view;
//            boolean isChecked = sw.isChecked();
//            if(isChecked){
//
//
//
//
//
//      }
//      public void swtich1Click2(View view){
//            Switch sw = (Switch)view;
//            boolean isChecked = sw.isChecked();
//            if(isChecked){
//                  SharedPreferencesUtils.SetType1(this,true);
//                  Toast.makeText(SetActivity.this, "开启2", Toast.LENGTH_SHORT).show();
//            }else {
//                  SharedPreferencesUtils.SetType1(this,false);
//                  Toast.makeText(SetActivity.this, "关闭2", Toast.LENGTH_SHORT).show();
//            }
//      }
//      public void swtich1Click3(View view){
//            Switch sw = (Switch)view;
//            boolean isChecked = sw.isChecked();
//            if(isChecked){
//                  SharedPreferencesUtils.SetType2(this,true);
//                  Toast.makeText(SetActivity.this, "开启3", Toast.LENGTH_SHORT).show();
//            }else {
//                  SharedPreferencesUtils.SetType2(this,false);
//                  Toast.makeText(SetActivity.this, "关闭3", Toast.LENGTH_SHORT).show();
//            }
//      }
//      public void swtich1Click4(View view){
//            Switch sw = (Switch)view;
//            boolean isChecked = sw.isChecked();
//            if(isChecked){
//                  SharedPreferencesUtils.SetType3(this,true);
//                  Toast.makeText(SetActivity.this, "开启4", Toast.LENGTH_SHORT).show();
//            }else {
//                  SharedPreferencesUtils.SetType3(this,false);
//                  Toast.makeText(SetActivity.this, "关闭4", Toast.LENGTH_SHORT).show();
//            }
//      }

      @Override
      public void initView() {

      }

      @Override
      public void initData() {

      }

      @Override
      public int setLayout() {
            return R.layout.activity_set;
      }
}
