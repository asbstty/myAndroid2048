package com.example.my2048project.utils;

import android.util.Log;

/**
 * Created by 866315 on 2015/10/9.
 */
public class LogUtils {
    private String tag = "sfnp";
    private static boolean D = false;
    private static boolean I = false;
    private static LogUtils instance = new LogUtils();

    private LogUtils() {

    }


    /**
     * 获取函数名称
     */
    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (null == sts) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod())
                continue;
            if (st.getClassName().equals(Thread.class.getName()))
                continue;
            if (st.getClassName().equals(this.getClass().getName()))
                continue;
            return "[" + st.getFileName() + ":" + st.getLineNumber() + "]";


        }
        return null;
    }

    private static String createMessage(String msg) {
        String functionName = instance.getFunctionName();
        String message = (null == functionName ? msg : (functionName + "-" + msg));
        return message;
    }

    /**
     * 打印出i信息
     */
    public static void i(String msg) {
        if (I) {
            String message = createMessage(msg);
            Log.i(instance.tag, message);
        }
    }

    public static void d(String msg) {
        if (D) {
            String message = createMessage(msg);
            Log.d(instance.tag, message);
        }
    }

}
