package com.example.yuanren123.jinchuan.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yuanren123 on 2017/8/23.
 */

public class VideoService extends Service {
      private MediaPlayer mediaPlayer ;


      @Nullable
      @Override
      public IBinder onBind(Intent intent) {
            return null;
      }

      @Override
      public void onCreate() {
            super.onCreate();
      }

      @Override
      public int onStartCommand(Intent intent,  int flags, int startId) {

            int musicId = intent.getIntExtra("musicId",0);
            String musicId1 = intent.getStringExtra("url");
            if (musicId == 0){
                  if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                  }
                  if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                  }

                  Uri uri  =  Uri.parse(musicId1);
                  mediaPlayer= MediaPlayer.create(this, uri);
                  try {
                        //设置是否循环播放
                        mediaPlayer.setLooping(false);
                        //设置播放起始点
                        mediaPlayer.seekTo(0);
                        //开始播放
                        mediaPlayer.start();
                  } catch (IllegalStateException e) {
                        e.printStackTrace();
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
                  return super.onStartCommand(intent, flags, startId);
            }else {
                  if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                  }
                  if (mediaPlayer != null){
                        mediaPlayer.release();
                        mediaPlayer = null;
                  }

                  mediaPlayer= MediaPlayer.create(this, musicId);
                  try {
                        //设置是否循环播放
                        mediaPlayer.setLooping(false);
                        //设置播放起始点
                        mediaPlayer.seekTo(0);
                        //开始播放
                        mediaPlayer.start();
                  } catch (IllegalStateException e) {
                        e.printStackTrace();
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
                  return super.onStartCommand(intent, flags, startId);
            }



      }

      @Override
      public void onDestroy() {
            super.onDestroy();
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                  mediaPlayer.stop();
            }
            if (mediaPlayer != null){
                  mediaPlayer.release();
                  mediaPlayer = null;
            }
            Log.d("xiaohuishengming", "onDestroy: service ");

      }
}
