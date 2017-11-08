package com.example.yuanren123.jinchuan.fragment.homefragment;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.base.BaseFragment;
import com.example.yuanren123.jinchuan.view.signview.ResolutionUtil;
import com.example.yuanren123.jinchuan.view.signview.SignAdapter;
import com.example.yuanren123.jinchuan.view.signview.SignEntity;
import com.example.yuanren123.jinchuan.view.signview.SignView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by yuanren123 on 2017/7/31.
 */
@ContentView(R.layout.fragment_home_two)
public class HomeTwoFragment extends BaseFragment {

     @ViewInject(R.id.main_two_cv)
      private SignView signView;

      private List<SignEntity> data;

      @ViewInject(R.id.main_two_date)
      private TextView tv_date;


      @Override
      public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            Calendar calendarToday = Calendar.getInstance();




           int month = calendarToday.get(Calendar.MONTH)+1;
           int  day = calendarToday.get(Calendar.DAY_OF_MONTH);

            tv_date.setText(month+"-"+day);


            int dayOfMonthToday = calendarToday.get(Calendar.DAY_OF_MONTH);
            data = new ArrayList<>();
            Random ran = new Random();
            for (int i = 0; i < 30; i++) {
                  SignEntity signEntity = new SignEntity();
                  if (dayOfMonthToday == i + 1)
                        signEntity.setDayType(2);
                  else
                        signEntity.setDayType((ran.nextInt(1000) % 2 == 0) ? 0 : 1);
                  data.add(signEntity);
            }
            ResolutionUtil resolutionUtil = ResolutionUtil.getInstance();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resolutionUtil.formatVertical(800));
            signView.setLayoutParams(layoutParams);

            SignAdapter signAdapter = new SignAdapter(data);
            signView.setAdapter(signAdapter);


      }

}
