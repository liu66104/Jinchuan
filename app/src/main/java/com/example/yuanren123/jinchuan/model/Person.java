package com.example.yuanren123.jinchuan.model;

import org.litepal.crud.DataSupport;

/**
 * Created by yuanren123 on 2017/8/18.
 */

public class Person extends DataSupport {
      private String name;
      private String age;
      public String getName() {
            return name;
      }
      public void setName(String name) {
            this.name = name;
      }
      public String getAge() {
            return age;
      }
      public void setAge(String age) {
            this.age =age;
      }
}
