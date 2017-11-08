package com.example.yuanren123.jinchuan.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import org.xutils.x;

/**
 * Created by yangshenglong on 16/11/22.
 */

public abstract class BaseActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(setLayout());
        x.view().inject(this);
        initView();
        initData();
        initTitle();

    }

    //初始化组件
    public abstract void initView();

    //初始化数据的方法
    public abstract void initData();

    //设置布局的
    public abstract int setLayout();


    //设置title文字
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    public void initMenuDrawable(@DrawableRes int drawable) {

    }

    public void doMenuClick() {

    }

    public <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }

    //初始化title
    public void initTitle() {

    }

    public void isShowWebViewCancle(boolean isShowCancle) {

    }



    @Override
    protected void onStop() {

        super.onStop();

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//
    }

    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
