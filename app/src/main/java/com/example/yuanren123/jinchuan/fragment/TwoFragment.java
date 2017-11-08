package com.example.yuanren123.jinchuan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.adapter.frag.RecyclerTwoAdapter;
import com.example.yuanren123.jinchuan.base.BaseFragment;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by yuanren123 on 2017/7/25.
 */

@ContentView(R.layout.fragment_two)
public class TwoFragment extends BaseFragment {
      @ViewInject(R.id.rv_two_fl)
      private RecyclerView rv;
      private RecyclerTwoAdapter adapter;



      @Override
      public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            adapter = new RecyclerTwoAdapter(getActivity());
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);

      }
}