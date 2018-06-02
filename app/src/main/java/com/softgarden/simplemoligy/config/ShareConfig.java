package com.softgarden.simplemoligy.config;

/**
 * Created by moligy
 * on 2018/5/6.
 */

public interface ShareConfig {
    /*** 分享的类型 */
    int SHARE_WEIXIN = 0;
    int SHARE_FRIEND = 1;
    int SHARE_QQ = 2;
    int SHARE_QQZONE = 3;
    int SHARE_SINA = 4;

    //        //==========友盟配置=============//
    //        // key
    //        let UMAppKey = "597adc04f5ade4020700132e"
    //        // channelid
    //        let UMChannelId = isDebug ? "PGY" : "App Store"
    //        // 微信的appKey和appSecret
    //        let UMWeChatAppKey = "wxf4c9cf709f733543"
    //        let UMWeChatAppSecret = "3ad3eb29d5aef37b11a44e05eed27e3c"
    //        // QQ的appKey和appSecret
    //        let UMQQAppKey = "1106222240"
    //        let UMQQAppSecret = "QJ62TqDLvOSz61PM"
    //        // 新浪的appKey和appSecret
    //        let UMSinaAppKey = "73421457"
    //        let UMSinaAppSecret = "a422be20542c594e06538f5db5b90abc"
    String WECHAT_SHARE_KEY = "";
    String WECHAT_SHARE_SECRET = "";
    String QQ_SHARE_KEY = "";
    String QQ_SHARE_SECRET = "";
    String SINA_SHARE_KEY = "";
    String SINA_SHARE_SECRET = "";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static final String REDIRECT_URL = "http://www.sina.com";

    public static final String REDIRECT_URL2 = "http://sns.whalecloud.com/sina2/callback";

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
