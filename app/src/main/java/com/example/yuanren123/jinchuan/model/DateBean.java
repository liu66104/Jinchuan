package com.example.yuanren123.jinchuan.model;

import org.litepal.crud.DataSupport;

/**
 * Created by yuanren123 on 2017/8/30.
 */

public class DateBean extends DataSupport {
      private int id;
      private int year;
      private int month;
      private int day;

      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      public int getYear() {
            return year;
      }

      public void setYear(int year) {
            this.year = year;
      }

      public int getMonth() {
            return month;
      }

      public void setMonth(int month) {
            this.month = month;
      }

      public int getDay() {
            return day;
      }

      public void setDay(int day) {
            this.day = day;
      }
}
