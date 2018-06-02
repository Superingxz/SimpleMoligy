package com.softgarden.baselibrary.utils;

/**
 * Desc sp管理类
 * Author feng
 * Date   2017/7/19.
 */

public class BaseSP {
    private static final String BASE_INFO = "baseInfo";
    private static final String USER_ID = "userId"; //用户id
//    private static final String PHONE = "phone"; //用户id
//    private static final String USER_NAME = "userName"; //用户名
//    private static final String IS_LOGIN = "isLogin"; //是否已登录
    private static final String TOKEN = "token";
    private static final String HEAD_IMG = "userHeadImg"; //头像
//    private static final String MONEY = "money"; //余额
//    private static final String SCORE = "score"; //积分
//    private static final String INVITE = "invite"; //合伙人id,邀请码
//    private static final String LEVEL = "level"; //合伙人id,邀请码
    private static final String SELECT_CITY_ID = "select_city_id"; //选择城市的id

    private static final String WATERMARK = "water_mark";//是否水印

    public static String getSelectCityId() {
        return (String) SPUtil.get(SELECT_CITY_ID, "");
    }

    public static void setSelectCityId(String selectCityId) {
        SPUtil.put(SELECT_CITY_ID, selectCityId);
    }

//    public static void setLevel(String level) {
//        SPUtil.put(LEVEL, level);
//    }
//
//    public static String getLevel() {
//        return (String) SPUtil.get(LEVEL, "");
//    }
//
//    public static void setPhone(String phone) {
//        SPUtil.put(PHONE, phone);
//    }
//
//    public static String getPHONE() {
//        return (String) SPUtil.get(PHONE, "");
//    }
//
//    public static void setMoney(String money) {
//        SPUtil.put(MONEY, money);
//    }
//
//    public static String getMoney() {
//        return (String) SPUtil.get(MONEY, "");
//    }
//
//    public static void setScore(String score) {
//        SPUtil.put(SCORE, score);
//    }
//
//    public static String getScore() {
//        return (String) SPUtil.get(SCORE, "");
//    }

    public static String getUserID() {
        return (String) SPUtil.get(USER_ID, "");
    }

    public static void seteUserID(String userId) {
        SPUtil.put(USER_ID, userId);
    }

//    public static String getUserName() {
//        return (String) SPUtil.get(USER_NAME, "");
//    }
//
//    public static void setUserName(String username) {
//        SPUtil.put(USER_NAME, username);
//    }
//
//    public static boolean getIsLogin() {
//        return (boolean) SPUtil.get(IS_LOGIN, false);
//    }
//
//    public static void setIsLogin(boolean isLogin) {
//        SPUtil.put(IS_LOGIN, isLogin);
//    }

    public static String getToken() {
        return (String) SPUtil.get(TOKEN, "");
    }

    public static void setToken(String token) {
        SPUtil.put(TOKEN, token);
    }

    public static String getHeadImg() {
        return (String) SPUtil.get(HEAD_IMG, "");
    }

    public static void setHeadImg(String headImg) {
        SPUtil.put(HEAD_IMG, headImg);
    }
//
//    public static String getInvite() {
//        return (String) SPUtil.get(INVITE, "");
//    }
//
//    public static void setInvite(String invite) {
//        SPUtil.put(INVITE, invite);
//    }

    public static String getInfo() {
        return (String) SPUtil.get(BASE_INFO, "");
    }

    public static void setInfo(String info) {
        SPUtil.put(BASE_INFO, info);
    }

    public static String getWatermark(){
        return (String) SPUtil.get(WATERMARK, "0");//默认不去水印
    }

    public static void setWatermark(String isWaterMark){
        SPUtil.put(WATERMARK, isWaterMark);
    }
}
