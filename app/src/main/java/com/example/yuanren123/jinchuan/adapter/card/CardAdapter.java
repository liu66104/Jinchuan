package com.example.yuanren123.jinchuan.adapter.card;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.activity.card.CardShowActivity;
import com.example.yuanren123.jinchuan.model.ResultBean;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by yuanren123 on 2017/10/27.
 */

public class CardAdapter extends BaseAdapter {

      private Context context;
      private List<ResultBean.RvBean> data;

      public CardAdapter(Context context, List<ResultBean.RvBean> data) {
            this.context = context;
            this.data = data;
            notifyDataSetChanged();
      }

      public void Add(List<ResultBean.RvBean> datas){
            for (int i = 0; i < datas.size(); i++) {
                  this.data.add(datas.get(i));
            }
            notifyDataSetChanged();
      }

      public int getCount() {
            return data.size();
      }

      @Override
      public Object getItem(int i) {
            return data.get(i);
      }

      @Override
      public long getItemId(int i) {
            return i;
      }

      @Override
      public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null){
                  viewHolder = new ViewHolder();
                  view = LayoutInflater.from(context).inflate(R.layout.item_card,null);
                  viewHolder.tv = view.findViewById(R.id.item_tv_card);
                  viewHolder.iv = view.findViewById(R.id.item_iv_card);
                  viewHolder.rl = view.findViewById(R.id.item_card_rl);
                  view.setTag(viewHolder);

            }else {
                  viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv.setText(data.get(i).getTitle());
            viewHolder.rl.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Intent intent  = new Intent(context, CardShowActivity.class);
                        intent.putExtra("url","http://"+data.get(i).getSummary());
                        context.startActivity(intent);
                  }
            });
            if (data.get(i).getIcon().equals("")||data.get(i).getIcon()==null){
                  Picasso.with(context).load(R.mipmap.ic_launcher).into(viewHolder.iv);
            }else {

                  Picasso.with(context).load(data.get(i).getIcon()).into(viewHolder.iv);
            }

            return view;
      }

      class ViewHolder{
            private RelativeLayout rl;
            private TextView tv;
            private ImageView iv;
      }

}
