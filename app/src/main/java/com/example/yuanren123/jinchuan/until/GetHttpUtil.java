package com.example.yuanren123.jinchuan.until;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuanren123 on 2017/8/22.
 */

public class GetHttpUtil {

      //get方式登录
      public static void requestNetForGet(final Handler handler, final String inputUrl) {

            //在子线程中操作网络请求
            new Thread(new Runnable() {
                  @Override
                  public void run() {
                        //urlConnection请求服务器，验证
                        try {
                              //1：url对象
                              URL url = new URL(inputUrl);
                              //2;url.openconnection
                              HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                              //3
                              conn.setRequestMethod("GET");
                              conn.setConnectTimeout(10 * 1000);
                              //4
                              int code = conn.getResponseCode();
                              if (code == 200) {
                                    InputStream inputStream = conn.getInputStream();
                                    String result = StreamUtils.stream2String(inputStream);
                                    System.out.println("=====================服务器返回的信息：：" + result);
                                    Message message = Message.obtain();
                                    if (result!=""){

                                          message.what = 1;
                                          Bundle bundleData = new Bundle();
                                          bundleData.putString("Result", result);
                                          message.setData(bundleData);
                                          handler.sendMessage(message);
                                    }

                              }
                        } catch (Exception e) {
                              handler.sendEmptyMessage(9);
                              e.printStackTrace();
                        }
                  }
            }).start();
      }

      public static void requestNetForGetTwo(final Handler handler, final String inputUrl) {

            //在子线程中操作网络请求
            new Thread(new Runnable() {
                  @Override
                  public void run() {
                        //urlConnection请求服务器，验证
                        try {
                              //1：url对象
                              URL url = new URL(inputUrl);
                              //2;url.openconnection
                              HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                              //3
                              conn.setRequestMethod("GET");
                              conn.setConnectTimeout(10 * 1000);
                              //4
                              int code = conn.getResponseCode();
                              if (code == 200) {
                                    InputStream inputStream = conn.getInputStream();
                                    String result = StreamUtils.stream2String(inputStream);
                                    System.out.println("=====================服务器返回的信息：：" + result);
                                    Message message = Message.obtain();
                                    if (result!=""){

                                          message.what = 2;
                                          Bundle bundleData = new Bundle();
                                          bundleData.putString("Result", result);
                                          message.setData(bundleData);
                                          handler.sendMessage(message);
                                    }

                              }
                        } catch (Exception e) {
                              e.printStackTrace();
                        }
                  }
            }).start();
      }

}
