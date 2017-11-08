package com.example.yuanren123.jinchuan.model;

import java.util.List;

/**
 * Created by yuanren123 on 2017/9/12.
 */

public class ResultBean {
      /**
       * rc : 200
       * rv : [{"id":"802122","timer":1505206608000,"icon":"","title":"百词斩4js","summary":"jpmp3.b0.upaiyun.com/jingying/word/2017/09/12/f084e4c5d92c4790e0b01c0c2bbc504f.js","vid":"4js","url":"http://www.ibianma.com/video/videoDetail.php?vid=4js"},{"id":"802112","timer":1505206537000,"icon":"","title":"百词斩3js","summary":"jpmp3.b0.upaiyun.com/jingying/word/2017/09/12/5f5d13014aa5656f358209740406ebbf.js","vid":"js3","url":"http://www.ibianma.com/video/videoDetail.php?vid=js3"},{"id":"802102","timer":1505206508000,"icon":"","title":"百词斩2js","summary":"jpmp3.b0.upaiyun.com/jingying/word/2017/09/12/3f7162f111739dde84230ebd06d724c3.js","vid":"2js","url":"http://www.ibianma.com/video/videoDetail.php?vid=2js"},{"id":"802062","timer":1505206341000,"icon":"","title":"百词斩js","summary":"jpmp3.b0.upaiyun.com/jingying/word/2017/09/12/4bb4d25dc0e0701f46d2bd0e9ed9772d.js","vid":"1js","url":"http://www.ibianma.com/video/videoDetail.php?vid=1js"}]
       */

      private int rc;
      private List<RvBean> rv;

      public int getRc() {
            return rc;
      }

      public void setRc(int rc) {
            this.rc = rc;
      }

      public List<RvBean> getRv() {
            return rv;
      }

      public void setRv(List<RvBean> rv) {
            this.rv = rv;
      }

      public static class RvBean {
            /**
             * id : 802122
             * timer : 1505206608000
             * icon :
             * title : 百词斩4js
             * summary : jpmp3.b0.upaiyun.com/jingying/word/2017/09/12/f084e4c5d92c4790e0b01c0c2bbc504f.js
             * vid : 4js
             * url : http://www.ibianma.com/video/videoDetail.php?vid=4js
             */

            private String id;
            private long timer;
            private String icon;
            private String title;
            private String summary;
            private String vid;
            private String url;

            public String getId() {
                  return id;
            }

            public void setId(String id) {
                  this.id = id;
            }

            public long getTimer() {
                  return timer;
            }

            public void setTimer(long timer) {
                  this.timer = timer;
            }

            public String getIcon() {
                  return icon;
            }

            public void setIcon(String icon) {
                  this.icon = icon;
            }

            public String getTitle() {
                  return title;
            }

            public void setTitle(String title) {
                  this.title = title;
            }

            public String getSummary() {
                  return summary;
            }

            public void setSummary(String summary) {
                  this.summary = summary;
            }

            public String getVid() {
                  return vid;
            }

            public void setVid(String vid) {
                  this.vid = vid;
            }

            public String getUrl() {
                  return url;
            }

            public void setUrl(String url) {
                  this.url = url;
            }
      }
}
