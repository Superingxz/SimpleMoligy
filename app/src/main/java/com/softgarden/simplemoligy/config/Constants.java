package com.softgarden.simplemoligy.config;

import android.os.Environment;

import com.softgarden.simplemoligy.BuildConfig;


/**
 * Created by moligy on 2017/10/30.
 */

public class Constants {
    public static final String APPNAME = "";
    public static final String MD5_KEY = "";

    //setting
    public static final String START_IMAGE_PATH = "START_IMAGE_PATH";//启动页图片路径
    public static final int PAGE_COUNT = 10;//每页请求数目
    public static final int GET_CODE_TIME = 60; //获取验证码的倒数时间
    public static final String MSG_COUNT = "msg_count";//未读消息数目
    public static final String SHOPCART_NUM = "shopCart_num"; //购物车数量
    public static final boolean IS_DEBUG = BuildConfig.DEBUG; //是否调试版本
    public static final long COURSEWARE_SIZE = 10 * 1024 * 1024; //上传课件大小限制

    /*** 接口参数 */
    //验证码类型:(0:没注册的手机:注册账号,1:已经注册的手机:重置密码)
    public static final String REGISTE_CODE = "0";
    public static final String RESET_REGISTE_CODE = "1";


    public static String getImageURL(String url) {
        return HostUrl.IMAG_HOST_URL + url;
    }

    public static String URL;
    public static String RES_URL = "http://ozfrxmff3.bkt.clouddn.com/";

    static {
        if (BuildConfig.DEBUG) {
//            URL = MyConstant.URL_TEST;
            URL = HostUrl.HOST_URL;
        } else {
            URL = HostUrl.HOST_URL;
        }
    }

    /**缓存文件夹路径 参数配置 */
    public final static class CACHE_PATH {

        public final static String SD_DATA = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/" + APPNAME + "/data";
        //文件存储位置
        public final static String SD_DOWNLOAD = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/" + APPNAME + "/download";
        //日志存储位置
        public final static String SD_LOG = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/" + APPNAME + "/log";
        //图片存储位置
        public final static String SD_IMAGE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/" + APPNAME + "/image";
        //图片缓存位置
        public final static String SD_IMAGECACHE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/" + APPNAME + "/imagecache";
    }
}
