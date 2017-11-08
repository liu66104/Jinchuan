package com.example.yuanren123.jinchuan.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.collect.CollectActivity;
import com.example.yuanren123.jinchuan.activity.plan.PlanActivity;
import com.example.yuanren123.jinchuan.activity.set.SetActivity;
import com.example.yuanren123.jinchuan.base.BaseFragment;
import com.example.yuanren123.jinchuan.model.DateBean;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import com.example.yuanren123.jinchuan.view.popwindows.RewritePopwindow;
import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by yuanren123 on 2017/7/25.
 */

@ContentView(R.layout.fragment_home_three)
public class ThreeFragment extends BaseFragment {
      @ViewInject(R.id.rl_three_collect)
      public RelativeLayout rl_collect;
      @ViewInject(R.id.rl_three_plan)
      private RelativeLayout rl_plan;
      private String name;
      private String figure;
      @ViewInject(R.id.main_cv)
      private CircleImageView cv;
      @ViewInject(R.id.tv_main_username)
      private TextView tv_name;
      @ViewInject(R.id.tv_frag_three_wordsNum)
      private TextView tv_words;
      @ViewInject(R.id.tv_frag_three_day)
      private TextView tv_day;
      @ViewInject(R.id.rl_three_call)
      private RelativeLayout rl_call;
      @ViewInject(R.id.rl_three_set)
      private RelativeLayout rl_set;

      private RewritePopwindow mPopwindow;

      @Override
      public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            List<DateBean> dateBeans = DataSupport.findAll(DateBean.class);
            tv_day.setText(dateBeans.size()+"");

            List<ReviewBean> reviewBean = DataSupport.findAll(ReviewBean.class);
            tv_words.setText(reviewBean.size()+"");

            rl_collect.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CollectActivity.class);
                        startActivity(intent);
                  }
            });

            rl_plan.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PlanActivity.class);
                        startActivity(intent);
                  }
            });

            rl_call.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        mPopwindow = new RewritePopwindow(getActivity(), itemsOnClick);
                        mPopwindow.showAtLocation(view,
                                  Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                  }
            });

            rl_set.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SetActivity.class);
                        startActivity(intent);
                  }
            });

      }
      //为弹出窗口实现监听类
      private View.OnClickListener itemsOnClick = new View.OnClickListener() {

            public void onClick(View v) {
                  mPopwindow.dismiss();
                  mPopwindow.backgroundAlpha(getActivity(), 1f);
                  switch (v.getId()) {
                        case R.id.weixinghaoyou:
                              ClipboardManager myClipboard;
                              myClipboard = (ClipboardManager)getActivity().getSystemService(CLIPBOARD_SERVICE);
                              String text = "jinchuanriyu";
                              ClipData myClip;
                              myClip = ClipData.newPlainText("微信", text);
                              myClipboard.setPrimaryClip(myClip);
                              Toast.makeText(getActivity(),"微信号已经复制", Toast.LENGTH_LONG).show();
                              break;
                        case R.id.pengyouquan:
                              Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"+8615542490557"));
                              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              startActivity(intent);
                              break;
                        case R.id.qqhaoyou:

                              try {
                                    //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=2040129784";//uin是发送过去的qq号码
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                              } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "请检查是否安装QQ", Toast.LENGTH_SHORT).show();

                              }


                        default:
                              break;
                  }
            }

      };
}
