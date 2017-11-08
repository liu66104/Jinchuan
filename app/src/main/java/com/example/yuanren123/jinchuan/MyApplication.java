package com.example.yuanren123.jinchuan;



import android.content.Context;
import com.example.yuanren123.jinchuan.view.signview.ResolutionUtil;
import org.litepal.LitePalApplication;
import org.litepal.exceptions.GlobalException;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends LitePalApplication {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ResolutionUtil.getInstance().init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    /**
     * Construct of LitePalApplication. Initialize application context.
     */
    public MyApplication() {
        sContext = this;
    }

    /**
     * Initialize to make LitePal ready to work. If you didn't configure LitePalApplication
     * in the AndroidManifest.xml, make sure you call this method as soon as possible. In
     * Application's onCreate() method will be fine.
     *
     * @param context
     * 		Application context.
     */
    public static void initialize(Context context) {
        sContext = context;
    }

    /**
     * Get the global application context.
     *
     * @return Application context.
     * @throws org.litepal.exceptions.GlobalException
     */
    public static Context getContext() {
        if (sContext == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return sContext;
    }
}
