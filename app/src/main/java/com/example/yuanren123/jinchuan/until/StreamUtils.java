package com.example.yuanren123.jinchuan.until;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuanren123 on 2017/8/22.
 */

public class StreamUtils {
      public static String stream2String(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();


            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                  out.write(buffer, 0, len);
            }


            in.close();
            out.close();


            return out.toString();
      }
}
