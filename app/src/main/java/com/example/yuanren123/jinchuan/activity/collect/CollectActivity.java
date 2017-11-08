package com.example.yuanren123.jinchuan.activity.collect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.adapter.collect.CollectAdapter;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.model.CollectBean;
import org.litepal.crud.DataSupport;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.List;

/**
 * Created by yuanren123 on 2017/8/9.
 */

@ContentView(R.layout.activity_collect)
public class CollectActivity extends BaseActivity {

      @ViewInject(R.id.recite_iv_back)
      private ImageView iv_back;
      @ViewInject(R.id.tv_frag_three_collectNum)
      private TextView tv_collectNum;
      @ViewInject(R.id.lv_frag_three)
      private RecyclerView recyclerView;
      private CollectAdapter adapter;
      private  List<CollectBean> collectBean;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            collectBean = DataSupport.findAll(CollectBean.class);
            tv_collectNum.setText(collectBean.size()+"");
            iv_back.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        finish();
                  }
            });
            adapter = new CollectAdapter(CollectActivity.this,collectBean,handler);
            recyclerView.setLayoutManager(new LinearLayoutManager(CollectActivity.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
      }
      Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                  Intent intent = new Intent(CollectActivity.this,CollectDetailActivity.class);
                  intent.putExtra("Ja",collectBean.get(msg.what).getJa());
                  intent.putExtra("Cx",collectBean.get(msg.what).getCx());
                  intent.putExtra("Pic",collectBean.get(msg.what).getPic());
                  intent.putExtra("Je",collectBean.get(msg.what).getJe());
                  intent.putExtra("Ce",collectBean.get(msg.what).getCe());
                  intent.putExtra("Ch",collectBean.get(msg.what).getCh());
                  intent.putExtra("Jaudio",collectBean.get(msg.what).getJaudio());
                  intent.putExtra("Jsentence",collectBean.get(msg.what).getJsentence());
                  intent.putExtra("Jvideo",collectBean.get(msg.what).getJvideo());
                  intent.putExtra("Tag",collectBean.get(msg.what).getTag());
                  startActivity(intent);
                  finish();
                  super.handleMessage(msg);
            }
      };



      @Override
      public void initView() {

      }

      @Override
      public void initData() {

      }

      @Override
      public int setLayout() {
            return R.layout.activity_collect;
      }
}
