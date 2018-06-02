package com.softgarden.simplemoligy.bean;

import com.softgarden.baselibrary.utils.BaseSP;
import com.softgarden.simplemoligy.utils.SP;

/**
 * Created by moligy
 * on 2018/3/24.
 */

public class UserBean {
    public String token;
    public String uid;
    public String mobile;
    public String email;
    public String username;
    public String avatar;
    public String sign;
    public String sex;
    public String birthdaytime;
    public String province;
    public String city;
    public String address;
    public String status;
    /**
     * 保存登录信息
     *
     * @param userBean
     */
    public static void saveLogin(UserBean userBean) {
        if (userBean == null) {
            return;
        }
        SP.setIsLogin(true);
        SP.setToken(userBean.token);
        SP.setUserName(userBean.username);
        SP.setHeadImg(userBean.avatar);
        BaseSP.setHeadImg(userBean.avatar);
        SP.seteUserID(userBean.uid);
        SP.setMobile(userBean.mobile);
        SP.setIsFirstEntry(true);
    }

    /**
     * 清除登录信息
     */
    public static void cleanLogin() {
        SP.setIsLogin(false);
        SP.setToken("");
        SP.setUserName("");
        SP.setHeadImg("");
        BaseSP.setHeadImg("");
        SP.seteUserID("");
        SP.setMobile("");
        cleanUser();
    }

    private static void cleanUser() {
        //        getUserBean().setHead(null);
        //getUserBean().setId(null);
        //        getUserBean().setNickname(null);
        //        getUserBean().setInvite(null);
        //        getUserBean().setScore(null);
        //        getUserBean().setMoney(null);
        //        getUserBean().setPhone(null);
        //        getUserBean().setLevel(null);


        SP.seteUserID("");
        //        BaseSP.setHeadImg("");
        //        BaseSP.setUserName("");
        //        BaseSP.setInvite("");
        //        BaseSP.setMoney("");
        //        BaseSP.setScore("");
        //        BaseSP.setPhone("");
        //        BaseSP.setLevel("");
    }
}
