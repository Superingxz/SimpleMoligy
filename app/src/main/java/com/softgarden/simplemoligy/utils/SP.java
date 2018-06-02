package com.softgarden.simplemoligy.utils;

import com.softgarden.baselibrary.BaseApplication;
import com.softgarden.baselibrary.utils.SPUtil;
import com.softgarden.simplemoligy.R;


/**
 * Desc sp管理类
 * Author feng
 * Date   2017/7/19.
 */

public class SP {
    private static final String IS_FIRST_ENTRY = "isFirstEntry";//是否第一次进去app
    private static final String IS_FIRST_LOGIN = "is_first_login";//是否第一次登陆
    private static final String IS_OTHER_LOGIN = "otherLogin";//信账号在其他设备登录
    private static final String ENTRY_TIME = "entry_time";//进入app次数
    private static final String BASE_INFO = "baseInfo";
    private static final String USER_ID = "userId"; //用户id
    private static final String TOURIST_ID = "touristId";//游客id
    //    private static final String PHONE = "phone"; //用户id
    private static final String USER_NAME = "userName"; //用户名
    private static final String IS_LOGIN = "isLogin"; //是否已登录
    private static final String IS_VIP = "is_vip";//是否是会员
    private static final String TOKEN = "token";
    private static final String HEAD_IMG = "headImg"; //头像
    private static final String MOBILE = "mobile";//手机号码
    private static final String SELECT_CITY_ID = "select_city_id"; //选择城市的id
    private static final String LOCATION_LONGITUDE = "location_longitude";//定位经度
    private static final String LOCATION_LATITUDE = "";//定位纬度
    private static final String SELECT_CITY = "select_city"; //手动选择城市 优先取这个
    private static final String LOCATION_CITY = "location_city"; //定位城市
    private static final String LOCATION_CITY_DISTRICT = "location_city_district"; //定位城市区

    public static int getEntryTime() {
        return (int) SPUtil.get(ENTRY_TIME, 0);
    }

    public static void setEntryTime(int entryTime) {
        SPUtil.put(ENTRY_TIME, entryTime);
    }

    public static boolean isFirstLogin() {
        return (boolean) SPUtil.get(IS_FIRST_LOGIN, true);
    }

    public static void setFirstLogin(boolean isFirstLogin) {
        SPUtil.put(IS_FIRST_LOGIN, isFirstLogin);
    }

    public static String getUserID() {
        return (String) SPUtil.get(USER_ID, "");
    }

    public static void seteUserID(String userId) {
        SPUtil.put(USER_ID, userId);
    }

    public static String getTouristID() {
        return (String) SPUtil.get(TOURIST_ID, "");
    }

    public static void setTouristID(String touristID) {
        SPUtil.put(TOURIST_ID, touristID);
    }

    public static boolean IsFirstEntry() {
        return (boolean) SPUtil.get(IS_FIRST_ENTRY, false);
    }

    public static void setIsFirstEntry(boolean isFirstEntry) {
        SPUtil.put(IS_FIRST_ENTRY, isFirstEntry);
    }

    public static boolean getIsLogin() {
        return (boolean) SPUtil.get(IS_LOGIN, false);
    }

    public static void setIsLogin(boolean isLogin) {
        SPUtil.put(IS_LOGIN, isLogin);
    }

    public static boolean getIsOtherLogin() {
        return (boolean) SPUtil.get(IS_OTHER_LOGIN, false);
    }

    public static void setIsOtherLogin(boolean isOtherLogin) {
        SPUtil.put(IS_OTHER_LOGIN, isOtherLogin);
    }

    public static String getToken() {
        return (String) SPUtil.get(TOKEN, "");
    }

    public static void setToken(String token) {
        SPUtil.put(TOKEN, token);
    }

    public static String getUserName() {
        return (String) SPUtil.get(USER_NAME, "");
    }

    public static void setUserName(String mobile) {
        SPUtil.put(USER_NAME, mobile);
    }


    public static String getHeadImg() {
        return (String) SPUtil.get(HEAD_IMG, "");
    }

    public static void setHeadImg(String headImg) {
        SPUtil.put(HEAD_IMG, headImg);
    }

    public static String getMobile() {
        return (String) SPUtil.get(MOBILE, "");
    }

    public static void setMobile(String mobile) {
        SPUtil.put(MOBILE, mobile);
    }

    /**
     * 手动选择的城市 -- 名称
     *
     * @return
     */
    public static String getSelectCity() {
        return (String) SPUtil.get(SELECT_CITY, "");
    }

    public static void putSelectCity(String selectCity) {
        SPUtil.put(SELECT_CITY, selectCity);
    }

    /**
     * 手动选择的城市  -- id
     *
     * @return
     */
    public static String getSelectCityId() {
        return (String) SPUtil.get(SELECT_CITY_ID, "");
    }

    public static void putSelectCityId(String selectCityId) {
        SPUtil.put(SELECT_CITY_ID, selectCityId);
    }

    public static String getLocationLongtude() {
        String longtude = (String) SPUtil.get(LOCATION_LONGITUDE, "0.00");
        return longtude;
    }

    public static void putLocationLongtude(String locationLongtude) {
        SPUtil.put(LOCATION_LONGITUDE, locationLongtude);
    }

    public static String getLocationLatitude() {
        String latitude = (String) SPUtil.get(LOCATION_LATITUDE, "0.00");
        return latitude;
    }

    public static void putLocationLatitude(String locationLatitude) {
        SPUtil.put(LOCATION_LATITUDE, locationLatitude);
    }


    /**
     * 自动定位的城市
     *
     * @return
     */
    public static String getLocationCity() {
        return (String) SPUtil.get(LOCATION_CITY, BaseApplication.getInstance().getString(R.string.locationing));
    }

    public static void putLocationCity(String locationCity) {
        SPUtil.put(LOCATION_CITY, locationCity);
    }

    /**
     * 自动定位的城市  -- 的区域
     *
     * @return
     */
    public static String getLocationCityDistrict() {
        return (String) SPUtil.get(LOCATION_CITY_DISTRICT, BaseApplication.getInstance().getString(R.string.location_city));
    }

    public static void putLocationCityDistrict(String locationCityDistrict) {
        SPUtil.put(LOCATION_CITY_DISTRICT, locationCityDistrict);
    }


    public static String getInfo() {
        return (String) SPUtil.get(BASE_INFO, "");
    }

    public static void setInfo(String info) {
        SPUtil.put(BASE_INFO, info);
    }
}
