package com.example.yuanren123.jinchuan.model;

import org.litepal.crud.DataSupport;

/**
 * Created by yuanren123 on 2017/8/17.
 */

public class OneWordsBean {

      private int id;

      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      private String ja;
      private String cx;
      private String ch;
      private String pic;
      private String je;
      private String ce;
      private String jaudio;
      private String jsentence;
      private String jvideo;
      private String order;
      //是显示句子还是单词
      private int type;
      //页面根据这个值来显示不同的布局
      private int showType;

      public int getShowType() {
            return showType;
      }

      public void setShowType(int showType) {
            this.showType = showType;
      }

      public int getTag() {
            return tag;
      }

      public void setTag(int tag) {
            this.tag = tag;
      }

      private int tag;

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

      public String getOrder() {
            return order;
      }

      public void setOrder(String order) {
            this.order = order;
      }

      public int getType() {
            return type;
      }

      public void setType(int type) {
            this.type = type;
      }

}
