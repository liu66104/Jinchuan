package com.example.yuanren123.jinchuan.view;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuanren123 on 2017/9/12.
 */

public class Player implements  MediaPlayer.OnCompletionListener,
          MediaPlayer.OnPreparedListener {
      public MediaPlayer mediaPlayer;

    //  private Timer mTimer = new Timer();
      private String videoUrl;
      private boolean pause;
      private int playPosition;

      public Player(String videoUrl) {

            this.videoUrl = videoUrl;
            try {
                  mediaPlayer = new MediaPlayer();
                  mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //  mediaPlayer.setOnBufferingUpdateListener(this);
                  mediaPlayer.setOnPreparedListener(this);
            } catch (Exception e) {
                  Log.e("mediaPlayer", "error", e);
            }


      }

      /*******************************************************
       * 通过定时器和Handler来更新进度条
       ******************************************************/

      /**
       * 来电话了
       */
      public void callIsComing() {
            if (mediaPlayer.isPlaying()) {
                  playPosition = mediaPlayer.getCurrentPosition();// 获得当前播放位置
                  mediaPlayer.stop();
            }
      }

      /**
       * 通话结束
       */
      public void callIsDown() {
            if (playPosition > 0) {
                  playNet(playPosition);
                  playPosition = 0;
            }
      }

      /**
       * 播放
       */
      public void play() {
            playNet(0);
      }

      /**
       * 重播
       */
      public void replay() {
            if (mediaPlayer.isPlaying()) {
                  mediaPlayer.seekTo(0);// 从开始位置开始播放音乐
            } else {
                  playNet(0);
            }
      }

      /**
       * 暂停
       */
      public boolean pause() {
            if (mediaPlayer.isPlaying()) {// 如果正在播放
                  mediaPlayer.pause();// 暂停
                  pause = true;
            } else {
                  if (pause) {// 如果处于暂停状态
                        mediaPlayer.start();// 继续播放
                        pause = false;
                  }
            }
            return pause;
      }

      /**
       * 停止
       */
      public void stop() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                  mediaPlayer.stop();
            }
      }

      @Override
      /**
       * 通过onPrepared播放
       */
      public void onPrepared(MediaPlayer arg0) {
            arg0.start();
            Log.e("mediaPlayer", "onPrepared");
      }

      @Override
      public void onCompletion(MediaPlayer arg0) {
            Log.e("mediaPlayer", "onCompletion");
      }

      /**
       * 播放音乐
       *
       * @param playPosition
       */
      private void playNet(int playPosition) {
            try {
                  mediaPlayer.reset();// 把各项参数恢复到初始状态
                  mediaPlayer.setDataSource(videoUrl);
                  mediaPlayer.prepare();// 进行缓冲
                  mediaPlayer.setOnPreparedListener(new MyPreparedListener(
                            playPosition));
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      private final class MyPreparedListener implements
                android.media.MediaPlayer.OnPreparedListener {
            private int playPosition;

            public MyPreparedListener(int playPosition) {
                  this.playPosition = playPosition;
            }

            @Override
            public void onPrepared(MediaPlayer mp) {
                  mediaPlayer.start();// 开始播放
                  if (playPosition > 0) {
                        mediaPlayer.seekTo(playPosition);
                  }
            }
      }
}