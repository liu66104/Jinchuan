package com.example.yuanren123.jinchuan.model;

import org.litepal.crud.DataSupport;

/**
 * Created by yuanren123 on 2017/9/6.
 */

public class ReviewBean extends DataSupport{

      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      private int id;
      private String ja;
      private String cx;
      private String ch;
      private String pic;
      private String je;
      private String ce;
      private String jaudio;
      private String jsentence;
      private String jvideo;
//      private String order;
      private String type;
      private int wrongNum;
      private int nowNuM;
      private int Tag;

      public int getTag() {
            return Tag;
      }

      public void setTag(int tag) {
            Tag = tag;
      }

      public int getNowNuM() {
            return nowNuM;
      }

      public void setNowNuM(int nowNuM) {
            this.nowNuM = nowNuM;
      }

      public int getWrongNum() {
            return wrongNum;
      }

      public void setWrongNum(int wrongNum) {
            this.wrongNum = wrongNum;
      }

      public String getJa() {
            return ja;
      }

      public void setJa(String ja) {
            this.ja = ja;
      }

      public String getCx() {
            return cx;
      }

      public void setCx(String cx) {
            this.cx = cx;
      }

      public String getCh() {
            return ch;
      }

      public void setCh(String ch) {
            this.ch = ch;
      }

      public String getPic() {
            return pic;
      }

      public void setPic(String pic) {
            this.pic = pic;
      }

      public String getJe() {
            return je;
      }

      public void setJe(String je) {
            this.je = je;
      }

      public String getCe() {
            return ce;
      }

      public void setCe(String ce) {
            this.ce = ce;
      }

      public String getJaudio() {
            return jaudio;
      }

      public void setJaudio(String jaudio) {
            this.jaudio = jaudio;
      }

      public String getJsentence() {
            return jsentence;
      }

      public void setJsentence(String jsentence) {
            this.jsentence = jsentence;
      }

      public String getJvideo() {
            return jvideo;
      }

      public void setJvideo(String jvideo) {
            this.jvideo = jvideo;
      }

//      public String getOrder() {
//            return order;
//      }
//
//      public void setOrder(String order) {
//            this.order = order;
//      }

      public String getType() {
            return type;
      }

      public void setType(String type) {
            this.type = type;
      }
}
