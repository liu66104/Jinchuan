package com.example.yuanren123.jinchuan.adapter.card;

/**
 * Created by yuanren123 on 2017/10/20.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.model.CardBean;
import com.example.yuanren123.jinchuan.until.NetWorkUtils;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.squareup.picasso.Picasso;
import java.io.IOException;

public class TestStackAdapter extends StackAdapter<CardBean> {

      public TestStackAdapter(Context context) {
            super(context);
      }

      @Override
      public void bindView(CardBean data, int position, CardStackView.ViewHolder holder) {
            if (holder instanceof ColorItemLargeHeaderViewHolder) {
                  ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
                  h.onBind(data, position);
            }
            if (holder instanceof ColorItemWithNoHeaderViewHolder) {
                  ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
                  h.onBind(data, position);
            }
            if (holder instanceof ColorItemViewHolder) {
                  ColorItemViewHolder h = (ColorItemViewHolder) holder;
                  h.onBind(data, position);
            }
      }

      @Override
      protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                  case R.layout.list_card_item_larger_header:
                        view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
                        return new ColorItemLargeHeaderViewHolder(view);
                  case R.layout.list_card_item_with_no_header:
                        view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
                        return new ColorItemWithNoHeaderViewHolder(view);
                  default:
                        view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
                        return new ColorItemViewHolder(view);
            }
      }

      @Override
      public int getItemViewType(int position) {
            if (position == 20) {//TODO TEST LARGER ITEM
                  return R.layout.list_card_item_larger_header;
            } else if (position == 20) {
                  return R.layout.list_card_item_with_no_header;
            }else {
                  return R.layout.list_card_item;
            }
      }



      /*
      *    用到的布局
      * */
      static class ColorItemViewHolder extends CardStackView.ViewHolder {
            View mLayout;
            View mContainerContent;
            TextView mTextTitle,cx,juzi,juzifanyi,tv_num;
            ImageView iv,iv_horn,iv_horn_word;
            Boolean video = true;
            MediaPlayer mediaPlayer;
            private Context context;

            public ColorItemViewHolder(View view) {
                  super(view);
                  mLayout = view.findViewById(R.id.frame_list_card_item);
                  mContainerContent = view.findViewById(R.id.container_list_content);
                  mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
                  iv = (ImageView)view.findViewById(R.id.iv);
                  cx = view.findViewById(R.id.cixing);
                  juzi = view.findViewById(R.id.juzi);
                  iv_horn_word= view.findViewById(R.id.iv_horn);
                  juzifanyi = view.findViewById(R.id.juzifanyi);
                  iv_horn = view.findViewById(R.id.iv_tan_horn);
                  tv_num = view.findViewById(R.id.text_list_card_num);
                  context = getContext();
            }

            @Override
            public void onItemExpand(boolean b) {
                  mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
            }

            public void onBind(final CardBean data, int position) {
                  mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data.getColor()), PorterDuff.Mode.SRC_IN);
                  mTextTitle.setText(data.getJa());
                  cx.setText(data.getCx());
                  juzi.setText(data.getJe());
                  juzifanyi.setText(data.getCe());
                  tv_num.setText(data.getTag());
                  iv_horn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                              if(NetWorkUtils.isNetworkAvailable((Activity) context)){
                                    if (video){
                                          video = false;
                                          if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                                mediaPlayer.stop();
                                          }else {

                                          }
                                          if (mediaPlayer != null){
                                                mediaPlayer.release();
                                                mediaPlayer = null;
                                          }
                                          mediaPlayer = new MediaPlayer();
                                          try {
                                                mediaPlayer.setDataSource(data.getJsentence());
                                                mediaPlayer.prepare();
                                          } catch (IOException e) {
                                                e.printStackTrace();
                                          }
                                          mediaPlayer.seekTo(400);
                                          mediaPlayer.start();
                                          mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mediaPlayer) {
                                                      video = true;
                                                }
                                          });
                                    }else {

                                    }
                              }else {
                                    Toast.makeText(getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                              }
//
                        }
                  });
                  iv_horn_word.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                              if(NetWorkUtils.isNetworkAvailable((Activity) context)){
                                    if (video){
                                          video = false;
                                          if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                                mediaPlayer.stop();
                                          }else {

                                          }
                                          if (mediaPlayer != null){
                                                mediaPlayer.release();
                                                mediaPlayer = null;
                                          }
                                          mediaPlayer = new MediaPlayer();
                                          try {
                                                mediaPlayer.setDataSource(data.getJaudio());
                                                mediaPlayer.prepare();
                                          } catch (IOException e) {
                                                e.printStackTrace();
                                          }
                                          mediaPlayer.seekTo(400);
                                          mediaPlayer.start();
                                          mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mediaPlayer) {
                                                      video = true;
                                                }
                                          });
                                    }else {

                                    }
                              }else {
                                    Toast.makeText(getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                              }
//
                        }
                  });


                  Picasso.with(getContext()).load(data.getPic()).into(iv);

      }

      }

      static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
            View mLayout;
            TextView mTextTitle;

            public ColorItemWithNoHeaderViewHolder(View view) {
                  super(view);
                  mLayout = view.findViewById(R.id.frame_list_card_item);
                  mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            }

            @Override
            public void onItemExpand(boolean b) {
            }

            public void onBind(CardBean data, int position) {
                  mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data.getColor()), PorterDuff.Mode.SRC_IN);
                  mTextTitle.setText(String.valueOf(position));
            }

      }

      static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
            View mLayout;
            View mContainerContent;
            TextView mTextTitle;

            public ColorItemLargeHeaderViewHolder(View view) {
                  super(view);
                  mLayout = view.findViewById(R.id.frame_list_card_item);
                  mContainerContent = view.findViewById(R.id.container_list_content);
                  mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            }

            @Override
            public void onItemExpand(boolean b) {
                  mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
            }

            @Override
            protected void onAnimationStateChange(int state, boolean willBeSelect) {
                  super.onAnimationStateChange(state, willBeSelect);
                  if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                        onItemExpand(true);
                  }
                  if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                        onItemExpand(false);
                  }
            }

            public void onBind(CardBean data, int position) {
                  mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data.getColor()), PorterDuff.Mode.SRC_IN);
                  mTextTitle.setText(String.valueOf(position));

                  itemView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                              ((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
                        }
                  });
            }

      }

}

