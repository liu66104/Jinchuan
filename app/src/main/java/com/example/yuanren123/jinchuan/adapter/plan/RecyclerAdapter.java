package com.example.yuanren123.jinchuan.adapter.plan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanren123 on 2017/8/9.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

      private Context context;
      private List<String > data;
      public RecyclerAdapter(Context context) {
            this.context = context;
         //   this.data = data;
            init();
      }

      private void init() {
            data = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                  data.add("日语教材"+i);
            }

      }

      @Override
      public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recycle_book, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
      }

      @Override
      public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                  ((MyViewHolder) holder).tv_name.setText(data.get(position));


      }

      @Override
      public int getItemCount() {
            return data != null && data.size() > 0 ? data.size() : 0;
      }

      public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name;
            public MyViewHolder(View itemView) {
                  super(itemView);
                  tv_name = itemView.findViewById(R.id.tv_item_book);
            }
      }

}
