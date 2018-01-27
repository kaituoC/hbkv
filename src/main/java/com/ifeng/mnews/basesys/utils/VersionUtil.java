package com.ifeng.mnews.basesys.utils;

/**
 * Created by changkt on 2018/1/18.
 */
public class VersionUtil {
    public static final String PREFIX = "/hbkv";
    public static final String VERSION = "/1.0";

//    public static final String HEALTH_CHECK = "/hbkv" + "/healthCheck";
    public static final String HEALTH_CHECK = "/healthCheck";
    public static final String HELLO = "/hbkv" + "/hello";

//    public static final String CURRENT_VERSION = "1.0";

    /**
     * 工具类不提供构造方法
     */
    private VersionUtil() {

    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public static String getVERSION() {
        return VERSION;
    }

    public static String getHealthCheck() {
        return HEALTH_CHECK;
    }

    public static String getHELLO() {
        return HELLO;
    }
}

