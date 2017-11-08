package com.example.yuanren123.jinchuan.adapter.frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.card.CardActivity;
import com.example.yuanren123.jinchuan.activity.review.ReviewActivity;
import com.example.yuanren123.jinchuan.model.ReviewBean;
import org.litepal.crud.DataSupport;
import java.util.List;

/**
 * Created by yuanren123 on 2017/8/28.
 */

public class RecyclerTwoAdapter extends RecyclerView.Adapter<RecyclerTwoAdapter.ViewHolder> {
      private Context context;
      private int[]pic = {R.mipmap.translate,R.mipmap.translate1,R.mipmap.hearing,R.mipmap.picturelean};
      private String[] title = {"日语选意","中文选词","听音辨意","卡片学习"};

      public RecyclerTwoAdapter(Context context) {
            this.context = context;
      }

      @Override
      public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_review,null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
      }

      @Override
      public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.iv.setImageResource(pic[position]);
            holder.tv.setText(title[position]);
            holder.rl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                        if (position == 3){

                              Intent intent = new Intent(context, CardActivity.class);
                              context.startActivity(intent);
                        }else {
                              List<ReviewBean>  PhraseBeans = DataSupport.findAll(ReviewBean.class);
                              if (PhraseBeans.size()<=4){
                                    Toast.makeText(context, "请先学习完再来复习", Toast.LENGTH_SHORT).show();

                              }else {

                                    SharedPreferences sp = context.getSharedPreferences("review", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putInt("type",position);
                                    editor.commit();
                                    Intent intent = new Intent(context, ReviewActivity.class);
                                    context.startActivity(intent);
                              }
                        }

                  }
            });

      }

      @Override
      public int getItemCount() {
            return 4;
      }

      class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView iv;
            private TextView tv;
            private RelativeLayout rl;

            public ViewHolder(View itemView) {
                  super(itemView);
                  iv = itemView.findViewById(R.id.iv_review_translate);
                  tv = itemView.findViewById(R.id.tv_review_title);
                  rl = itemView.findViewById(R.id.rl_review);
            }
      }

}
