package com.example.yuanren123.jinchuan.adapter.collect;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.model.CollectBean;
import java.util.List;

/**
 * Created by yuanren123 on 2017/9/6.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {

      private List<CollectBean> datas;
      private Context context;
      private Handler handler;

      public CollectAdapter(Context context, List<CollectBean> datas, Handler handler) {
            this.datas = datas;
            this.context = context;
            this.handler = handler;
      }

      @Override
      public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_frag_three,null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
      }

      @Override
      public void onBindViewHolder(ViewHolder holder, final int position) {

               holder.tv_name.setText(datas.get(position).getJa());
               holder.tv_time.setText(datas.get(position).getTimer());
               holder.iv_collect.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                       handler.sendEmptyMessage(position);
                     }
               });

      }

      @Override
      public int getItemCount() {
            return datas.size();
      }

      public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tv_name,tv_time;
            private ImageView iv_collect;

            public ViewHolder(View itemView) {
                  super(itemView);
                  tv_name = itemView.findViewById(R.id.tv_itemCollect_name);
                  tv_time = itemView.findViewById(R.id.tv_itemCollect_time);
                  iv_collect = itemView.findViewById(R.id.iv_collect_detail);
            }
      }
}
