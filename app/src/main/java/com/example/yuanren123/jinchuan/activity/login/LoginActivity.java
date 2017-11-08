package com.example.yuanren123.jinchuan.activity.login;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.yuanren123.jinchuan.MainActivity;
import com.example.yuanren123.jinchuan.R;
import com.example.yuanren123.jinchuan.base.BaseActivity;
import com.example.yuanren123.jinchuan.until.HttpUtils;
import com.example.yuanren123.jinchuan.until.SharedPreferencesUtils;
import com.mob.MobSDK;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.HashMap;
import java.util.Map;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by yuanren123 on 2017/8/7.
 */

@ContentView(R.layout.activity_register)
public class LoginActivity extends BaseActivity {
      private EventHandler handler;
      @ViewInject(R.id.yanzhengma)
      private EditText yanzhengma;
      @ViewInject(R.id.shoujihao)
      private EditText shoujihao;

      @ViewInject(R.id.btn_reg_tijiao)
      private Button btn;
      @ViewInject(R.id.btn_reg_tijiao1)
      private Button btn1;
      @ViewInject(R.id.btn_reg_send)
      private Button btn_send;
      private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
      private int i1;
      private AlertDialog dialog;
      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            x.view().inject(this);
            // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                  // 检查该权限是否已经获取
                  int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                  // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                  if (i != PackageManager.PERMISSION_GRANTED) {
                        // 如果没有授予该权限，就去提示用户请求
                        showDialogTipUserRequestPermission();
                  }
                        if(SharedPreferencesUtils.getIsFirst(this,"0")){
                              MobSDK.init(this, "21531402a37f4", "e28176d91e3e541e4ec0639ed36a58aa");
                              i1 =60;
                              handler = new EventHandler() {
                                    @Override
                                    public void afterEvent(int event, int result, Object data) {
                                          if (result == SMSSDK.RESULT_COMPLETE) {
                                                Log.d("kankanliucheng", "收到回掉 ");
                                                //回调完成
                                                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                                      //提交验证码成功
                                                      runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                  //   Log.d("kankanliucheng", "成功 ");
                                                                  Toast.makeText(LoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                                                  mCodeHandler.sendEmptyMessage(0);
                                                            }
                                                      });
                                                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                                      //获取验证码成功
                                                      runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                  Log.d("kankanliucheng", "发送了 ");
                                                                  Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                                            }
                                                      });
                                                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {

                                                }
                                          } else {
                                                ((Throwable) data).printStackTrace();
                                                Throwable throwable = (Throwable) data;
                                                try {
                                                      JSONObject obj = new JSONObject(throwable.getMessage());
                                                      final String des = obj.optString("detail");
                                                      if (!TextUtils.isEmpty(des)) {
                                                            runOnUiThread(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                        Toast.makeText(LoginActivity.this, "提交错误信息", Toast.LENGTH_SHORT).show();
                                                                  }
                                                            });
                                                      }
                                                } catch (JSONException e) {
                                                      e.printStackTrace();
                                                }
                                          }
                                    }
                              };
                              SMSSDK.registerEventHandler(handler);
                              btn_send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

//                              //获取验证码
                                          String number = shoujihao.getText().toString();
                                          if (isMobileNO(number)){
                                                SMSSDK.getVerificationCode("86", number);
                                                btn_send.setClickable(false);
                                                new Thread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                            for (; i1 > 0; i1--) {
                                                                  mCodeHandler.sendEmptyMessage(-1);
                                                                  if (i1 <= 0) {
                                                                        break;
                                                                  }
                                                                  try {
                                                                        Thread.sleep(1000);
                                                                  } catch (InterruptedException e) {
                                                                        e.printStackTrace();
                                                                  }
                                                            }
                                                            //倒计时结束执行
                                                            mCodeHandler.sendEmptyMessage(-2);
                                                      }
                                                }).start();

                                          }else {
                                                Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                                          }
                                    }

                              });
                              yanzhengma.addTextChangedListener(new MyTextWatcher());

                        }else {

                              Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                              startActivity(intent);
                              finish();
                        }


            }else {
                  if(SharedPreferencesUtils.getIsFirst(this,"0")){
                        MobSDK.init(this, "21531402a37f4", "e28176d91e3e541e4ec0639ed36a58aa");

                        i1 =60;

                        handler = new EventHandler() {
                              @Override
                              public void afterEvent(int event, int result, Object data) {
                                    if (result == SMSSDK.RESULT_COMPLETE) {
                                          Log.d("kankanliucheng", "收到回掉 ");
                                          //回调完成
                                          if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                                //提交验证码成功
                                                runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                            //   Log.d("kankanliucheng", "成功 ");
                                                            Toast.makeText(LoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                                            mCodeHandler.sendEmptyMessage(0);

                                                      }
                                                });

                                          } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                                //获取验证码成功
                                                runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                            Log.d("kankanliucheng", "发送了 ");
                                                            Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                                      }
                                                });
                                          } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {


                                          }
                                    } else {
                                          ((Throwable) data).printStackTrace();
                                          Throwable throwable = (Throwable) data;
                                          try {
                                                JSONObject obj = new JSONObject(throwable.getMessage());
                                                final String des = obj.optString("detail");
                                                if (!TextUtils.isEmpty(des)) {
                                                      runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                  Toast.makeText(LoginActivity.this, "提交错误信息", Toast.LENGTH_SHORT).show();
                                                            }
                                                      });
                                                }
                                          } catch (JSONException e) {
                                                e.printStackTrace();
                                          }

                                    }
                              }
                        };
                        SMSSDK.registerEventHandler(handler);
                        btn_send.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {

//                              //获取验证码
                                    String number = shoujihao.getText().toString();
                                    if (isMobileNO(number)){
                                          SMSSDK.getVerificationCode("86", number);
                                          btn_send.setClickable(false);
                                          new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                      for (; i1 > 0; i1--) {
                                                            mCodeHandler.sendEmptyMessage(-1);
                                                            if (i1 <= 0) {
                                                                  break;
                                                            }
                                                            try {
                                                                  Thread.sleep(1000);
                                                            } catch (InterruptedException e) {
                                                                  e.printStackTrace();
                                                            }
                                                      }
                                                      //倒计时结束执行
                                                      mCodeHandler.sendEmptyMessage(-2);
                                                }
                                          }).start();

                                    }else {
                                          Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                                    }
                              }

                        });
                        yanzhengma.addTextChangedListener(new MyTextWatcher());

                  }else {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                  }

            }

      }


      private class MyTextWatcher implements TextWatcher {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  String number = yanzhengma.getText().toString();
                  Log.d("wasdadasdaw", "onTextChanged: "+number.length());
                  if (number.length() == 4) {
                        btn.setVisibility(View.VISIBLE);
                        btn1.setVisibility(View.GONE);

                  } else {
                        btn.setVisibility(View.GONE);
                        btn1.setVisibility(View.VISIBLE);
                  }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
      }

      /**
       * 倒计时Handler
       */

      Handler mCodeHandler = new Handler() {
            public void handleMessage(Message msg) {
                  if (msg.what == -1) {
                        //修改控件文本进行倒计时  i 以60秒倒计时为例
                        btn_send.setText( i1+" s");
                  } else if (msg.what == -2) {
                        //修改控件文本，进行重新发送验证码
                        btn_send.setText("重新发送");
                        btn_send.setClickable(true);
                        i1 = 60;
                  }else if(msg.what == 0){

                        new MyThread1().start();

                  }else if (msg.what == 5){
                        SharedPreferencesUtils.goFirst(LoginActivity.this,"0");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                  }
            }

            ;
      };


      private boolean isMobileNO(String phone) {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
            String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            if (TextUtils.isEmpty(phone)) {
                  return false;
            } else {
                  return
                            phone.matches(telRegex);
            }
      }


      // 提示用户该请求权限的弹出框
      private void showDialogTipUserRequestPermission() {

            new AlertDialog.Builder(this)
                      .setTitle("存储权限不可用")
                      .setMessage("由于今川小词需要获取存储空间，为你存储单词；\n否则，您将无法正常使用今川小词")
                      .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                  startRequestPermission();
                            }
                      })
                      .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                  finish();
                            }
                      }).setCancelable(false).show();
      }

      // 开始提交请求权限
      private void startRequestPermission() {
            ActivityCompat.requestPermissions(this,permissions , 321);
      }

      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 321) {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                              // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                              boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                              if (!b) {
                                    // 用户还是想用我的 APP 的
                                    // 提示用户去应用设置界面手动开启权限

                                    showDialogTipUserGoToAppSettting();
                              } else
                                    finish();
                        } else {

                              Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                              if(SharedPreferencesUtils.getIsFirst(this,"0")){
                                    MobSDK.init(this, "21531402a37f4", "e28176d91e3e541e4ec0639ed36a58aa");

                                    i1 =60;

                                    handler = new EventHandler() {
                                          @Override
                                          public void afterEvent(int event, int result, Object data) {
                                                if (result == SMSSDK.RESULT_COMPLETE) {
                                                      Log.d("kankanliucheng", "收到回掉 ");
                                                      //回调完成
                                                      if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                                            //提交验证码成功
                                                            runOnUiThread(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                        //   Log.d("kankanliucheng", "成功 ");
                                                                        Toast.makeText(LoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                                                        mCodeHandler.sendEmptyMessage(0);

                                                                  }
                                                            });

                                                      } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                                            //获取验证码成功
                                                            runOnUiThread(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                        Log.d("kankanliucheng", "发送了 ");
                                                                        Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                                                  }
                                                            });
                                                      } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {


                                                      }
                                                } else {
                                                      ((Throwable) data).printStackTrace();
                                                      Throwable throwable = (Throwable) data;
                                                      try {
                                                            JSONObject obj = new JSONObject(throwable.getMessage());
                                                            final String des = obj.optString("detail");
                                                            if (!TextUtils.isEmpty(des)) {
                                                                  runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                              Toast.makeText(LoginActivity.this, "提交错误信息", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                  });
                                                            }
                                                      } catch (JSONException e) {
                                                            e.printStackTrace();
                                                      }

                                                }
                                          }
                                    };
                                    SMSSDK.registerEventHandler(handler);
                                    btn_send.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

//                              //获取验证码
                                                String number = shoujihao.getText().toString();
                                                if (isMobileNO(number)){
                                                      SMSSDK.getVerificationCode("86", number);
                                                      btn_send.setClickable(false);
                                                      new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                  for (; i1 > 0; i1--) {
                                                                        mCodeHandler.sendEmptyMessage(-1);
                                                                        if (i1 <= 0) {
                                                                              break;
                                                                        }
                                                                        try {
                                                                              Thread.sleep(1000);
                                                                        } catch (InterruptedException e) {
                                                                              e.printStackTrace();
                                                                        }
                                                                  }
                                                                  //倒计时结束执行
                                                                  mCodeHandler.sendEmptyMessage(-2);
                                                            }
                                                      }).start();

                                                }else {
                                                      Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                                                }
                                          }

                                    });
                                    yanzhengma.addTextChangedListener(new MyTextWatcher());

                              }else {

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                              }
                        }
                  }
            }
      }


      // 提示用户去应用设置界面手动开启权限

      private void showDialogTipUserGoToAppSettting() {

            dialog = new AlertDialog.Builder(this)
                      .setTitle("存储权限不可用")
                      .setMessage("请在-应用设置-权限-中，允许支付宝使用存储权限来保存用户数据")
                      .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                  // 跳转到应用设置界面
                                  goToAppSetting();
                            }
                      })
                      .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                  finish();
                            }
                      }).setCancelable(false).show();
      }

      // 跳转到当前应用的设置界面
      private void goToAppSetting() {
            Intent intent = new Intent();

            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);

            startActivityForResult(intent, 123);
      }

      //
      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 123) {

                  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // 检查该权限是否已经获取
                        int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (i != PackageManager.PERMISSION_GRANTED) {
                              // 提示用户应该去应用设置界面手动开启权限
                              showDialogTipUserGoToAppSettting();
                        } else {
                              if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                              }
                              Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();

                        }
                  }
            }
      }


      public void tijiao(View view) {
            String number = yanzhengma.getText().toString();
            String number1 = shoujihao.getText().toString();
            SMSSDK.submitVerificationCode("86", number1, number);
      }

            public class MyThread1 extends Thread {
                  @Override
                  public void run() {
                        String number1 = shoujihao.getText().toString();
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("contact", number1);
                        params.put("content", "今川小词app登陆");

                        //服务器请求路径
                        String strUrlPath = "http://www.ibianma.com/more/feedback.php";
                        String strResult= HttpUtils.submitPostData(strUrlPath,params, "utf-8");
                        Log.d("ewqew1", strResult);

                        mCodeHandler.sendEmptyMessage(5);
                  }
            }



      @Override
      public void initView() {

      }

      @Override
      public void initData() {

      }

      @Override
      protected void onDestroy() {
            super.onDestroy();
            mCodeHandler.removeCallbacksAndMessages(null);
            try {
                  SMSSDK.unregisterAllEventHandler();
            }catch (Exception e){

            }

      }

      @Override
      public int setLayout() {
            return R.layout.activity_register;
      }


}
