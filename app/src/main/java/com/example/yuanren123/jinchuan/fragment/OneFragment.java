package com.example.yuanren123.jinchuan.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.words.ReciteWordsActivity;
import com.example.yuanren123.jinchuan.base.BaseFragment;
import com.example.yuanren123.jinchuan.fragment.homefragment.HomeOneFragment;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yuanren123 on 2017/7/25.
 */
@ContentView(R.layout.fragment_one)
public class OneFragment extends BaseFragment {
      @ViewInject(R.id.vp_home)
      private ViewPager vp;
      //点的group布局
      @ViewInject(R.id.pointGroup)
      private LinearLayout pointGroup;
      @ViewInject(R.id.btn_one_go)
      private Button btn_one_go;
      @ViewInject(R.id.ll_frag_one)
      private LinearLayout ll_frag_one;
      @ViewInject(R.id.ll_frag_two)
      private LinearLayout ll_frag_two;
      @ViewInject(R.id.ll_frag_three)
      private LinearLayout ll_frag_three;
      @ViewInject(R.id.tv_frag_review)
      private TextView tv_frag_review;


      private ImageView mPreSelectedBt;

      private LocalBroadcastManager broadcastManager;
      private IntentFilter intentFilter;
      private BroadcastReceiver mReceiver;


      @Override
      public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Calendar calendarToday = Calendar.getInstance();
            int month = calendarToday.get(Calendar.MONTH)+1;
            int  day = calendarToday.get(Calendar.DAY_OF_MONTH);
            int year = calendarToday.get(Calendar.YEAR);
            boolean x = SharedPreferencesUtils.getIsComplete(getActivity(),year+""+month+""+day);
            if (x){
                  ll_frag_one.setVisibility(View.GONE);
                  ll_frag_two.setVisibility(View.VISIBLE);
                  ll_frag_three.setVisibility(View.GONE);
            }else {
                  ll_frag_one.setVisibility(View.VISIBLE);
                  ll_frag_two.setVisibility(View.GONE);
                  ll_frag_three.setVisibility(View.GONE);
            }

            SharedPreferences sp = getActivity().getSharedPreferences("Plan", Context.MODE_PRIVATE);
            String day1 = sp.getString("day","");

            if (day1.equals("")||day1 == null){
                  ll_frag_one.setVisibility(View.GONE);
                  ll_frag_two.setVisibility(View.GONE);
                  ll_frag_three.setVisibility(View.VISIBLE);
            }



            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
            vp.setAdapter(adapter);

            tv_frag_review.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        sendBroadcast();
                  }
            });



//            for (int i = 0; i < 2; i++) {
//                  ImageView bt = new ImageView(getActivity());
//                  bt.setBackgroundResource(R.mipmap.bullet_white);
//                  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT);
//                  params.rightMargin = 20;
//                  bt.setLayoutParams(params);
//                  pointGroup.addView(bt);
//            }
//            vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                  @Override
//                  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                        if (mPreSelectedBt != null) {
//                              mPreSelectedBt.setBackgroundResource(R.mipmap.bullet_white);
//                        }
//                        ImageView currentBt = (ImageView) pointGroup.getChildAt(position);
//                        currentBt.setBackgroundResource(R.drawable.point_selector);
//                        mPreSelectedBt = currentBt;
//                  }
//                  @Override
//                  public void onPageSelected(int position) {
//
//                  }
//                  @Override
//                  public void onPageScrollStateChanged(int state) {
//
//                  }
//            });

            btn_one_go.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ReciteWordsActivity.class);
                        getActivity().startActivity(intent);
                        SharedPreferences sp = getActivity().getSharedPreferences("words", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("wordsNum", 0);
                        editor.commit();
                  }
            });


            broadcastManager = LocalBroadcastManager.getInstance(getActivity());
            intentFilter = new IntentFilter();
            intentFilter.addAction("plan");
            mReceiver = new BroadcastReceiver() {
                  @Override
                  public void onReceive(Context context, Intent intent) {

                        Calendar calendarToday = Calendar.getInstance();
                        int month = calendarToday.get(Calendar.MONTH)+1;
                        int  day = calendarToday.get(Calendar.DAY_OF_MONTH);
                        int year = calendarToday.get(Calendar.YEAR);
                        boolean x = SharedPreferencesUtils.getIsComplete(getActivity(),year+""+month+""+day);
                        if (x){
                              ll_frag_one.setVisibility(View.GONE);
                              ll_frag_two.setVisibility(View.VISIBLE);
                              ll_frag_three.setVisibility(View.GONE);
                        }else {
                              ll_frag_one.setVisibility(View.VISIBLE);
                              ll_frag_two.setVisibility(View.GONE);
                              ll_frag_three.setVisibility(View.GONE);
                        }

                  }
            };
            broadcastManager.registerReceiver(mReceiver, intentFilter);




      }
      public class ViewPagerAdapter extends FragmentStatePagerAdapter {
            private List<Fragment> datas;
            //构造方法
            public ViewPagerAdapter(FragmentManager fm) {
                  super(fm);
                  datas = new ArrayList<>();
                  datas.add(new HomeOneFragment());
//                  datas.add(new HomeTwoFragment());
            }
            @Override
            public Fragment getItem(int position) {
                  return datas.get(position);
            }

            @Override
            public int getCount() {
                  return datas == null ? 0 : datas.size();
            }
      }


      public void sendBroadcast() {
            Intent intent = new Intent("goReview");
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

      }

}
