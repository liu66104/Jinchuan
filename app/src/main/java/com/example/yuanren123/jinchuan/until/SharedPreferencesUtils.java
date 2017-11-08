package com.example.yuanren123.jinchuan.until;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yuanren123 on 2017/8/18.
 */

public class SharedPreferencesUtils {

      public static final String IS_FIRST = "is_first";


      //获取是否第一次进入app
      public static boolean getIsFirst(Context context, String tel) {
            Context ctx = context;
            SharedPreferences sp = ctx.getSharedPreferences(IS_FIRST + tel, Context.MODE_PRIVATE);
            boolean isFirst = sp.getBoolean(IS_FIRST + tel, true);
            return isFirst;
      }

      public static void goFirst(Context context, String tel) {
            SharedPreferences sp = context.getSharedPreferences(IS_FIRST + tel, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putBoolean(IS_FIRST + tel, false);
            editor.commit();
      }


      //保存制定的学习计划
      public static void savePlan(Context context, int BookType, String day, String wordsNum) {
            SharedPreferences sp = context.getSharedPreferences("Plan", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putInt("BookType", BookType);
            editor.putString("day", day);
            editor.putString("wordsNum", wordsNum);
            editor.commit();
      }

      //保存
      public static void saveStudyPlan(Context context, int wordsNum) {
            SharedPreferences sp = context.getSharedPreferences("StudyPlan", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putInt("wordsNum", wordsNum);
            editor.commit();
      }

      //判断是否完成学习计划
      public static void goComplete(Context context, String date) {
            SharedPreferences sp = context.getSharedPreferences(IS_FIRST + date, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putBoolean(IS_FIRST + date, true);
            editor.commit();
      }

      public static boolean getIsComplete(Context context, String date) {
            Context ctx = context;
            SharedPreferences sp = ctx.getSharedPreferences(IS_FIRST + date, Context.MODE_PRIVATE);
            boolean isFirst = sp.getBoolean(IS_FIRST + date, false);
            return isFirst;
      }

      public static void saveStudyProgress(Context context, int wordsNum) {
            SharedPreferences sp = context.getSharedPreferences("StudyProgress", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putInt("Progress", wordsNum);
            editor.commit();
      }

      public static int getStudyProgress(Context context) {
            SharedPreferences sp = context.getSharedPreferences("StudyProgress", Context.MODE_PRIVATE);
            int wordsNum = sp.getInt("Progress", 0);
            return wordsNum;
      }

      public static void saveStudyProgressTwo(Context context, int wordsNum) {
            SharedPreferences sp = context.getSharedPreferences("StudyProgressTwo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putInt("ProgressTwo", wordsNum);
            editor.commit();
      }

      public static int getStudyProgressTwo(Context context) {
            SharedPreferences sp = context.getSharedPreferences("StudyProgressTwo", Context.MODE_PRIVATE);
            int wordsNum = sp.getInt("ProgressTwo", 0);
            return wordsNum;
      }


      //保存请求数据时间，时间戳
      public static void SaveTimer(Context context, String timer) {
            SharedPreferences sp = context.getSharedPreferences("Timer", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putString("Timer", timer);
            editor.commit();

      }

      public static String getTimer(Context context) {
            SharedPreferences sp = context.getSharedPreferences("Timer", Context.MODE_PRIVATE);
            String timer = sp.getString("Timer","");
            if (timer.equals("")||timer==null){
                  return "0";
            }else {
                  return timer;
            }

      }
      public static void ChooseResult(Context context,boolean choose){
            SharedPreferences sp = context.getSharedPreferences("ChooseResult", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putBoolean("choose", choose);
            editor.commit();
      }
      public static boolean Choose(Context context){
            SharedPreferences sp = context.getSharedPreferences("ChooseResult", Context.MODE_PRIVATE);
            boolean choose = sp.getBoolean("choose", false);
            return choose;
      }
      public static void SetShow(Context context,boolean choose){
            SharedPreferences sp = context.getSharedPreferences("SetShow", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putBoolean("show", choose);
            editor.commit();
      }
      public static boolean GetSetShow(Context context){
            SharedPreferences sp = context.getSharedPreferences("SetShow", Context.MODE_PRIVATE);
            boolean choose = sp.getBoolean("show", false);
            return choose;
      }
      public static void SetType1(Context context,boolean choose){
            SharedPreferences sp = context.getSharedPreferences("SetType1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putBoolean("type1", choose);
            editor.commit();
      }
      public static boolean GetSetType1(Context context){
            SharedPreferences sp = context.getSharedPreferences("SetType1", Context.MODE_PRIVATE);
            boolean choose = sp.getBoolean("type1", false);
            return choose;
      }
      public static void SetType2(Context context,boolean choose){
            SharedPreferences sp = context.getSharedPreferences("SetType2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putBoolean("type2", choose);
            editor.commit();
      }
      public static boolean GetSetType2(Context context){
            SharedPreferences sp = context.getSharedPreferences("SetType2", Context.MODE_PRIVATE);
            boolean choose = sp.getBoolean("type2", false);
            return choose;
      }
      public static void SetType3(Context context,boolean choose){
            SharedPreferences sp = context.getSharedPreferences("SetType3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.putBoolean("type3", choose);
            editor.commit();
      }
      public static boolean GetSetType3(Context context){
            SharedPreferences sp = context.getSharedPreferences("SetType3", Context.MODE_PRIVATE);
            boolean choose = sp.getBoolean("type3", false);
            return choose;
      }

}
