package com.dongao.oa.utils;

/**
 * Created by Thinkpad on 2016/9/19.
 */
public class MessageStatus {
    public static short SUCCEED = 0;
    public static short LOCK = 1; //锁定
    public static short TIMEOUT = 2;//超时
    public static short WRONG = 3; //用户密码错误
    public static short NONENTITY = 4;//不存在
    public static short NOTNULL = 5;//不能为空
    public static short FAILURE = 7;//失败
    public static short NOTLOGIN = 8;//未登陆
    public static short LOG_USER_LABEL_SUCCEED = 508;
    public static short LOG_USER_BRAND_SUCCEED = 509;
    public static short LOG_USER_MASTER_SUCCEED = 510;
    public static short LOG_USER_MASTERTYPE_SUCCEED = 511;
    public static short LOG_USER_NEWS_SUCCEED = 512;
    public static short LOG_USER_VIEWSECTION_SUCCEED = 513;
    public static short LOG_USER_VERSION_SUCCEED = 514;
    // 用户
    public static short LOG_USER_SUCCEED = 41;
    public static short LOG_COUNTRY_SUCCEED = 515;
    public static short LOG_CATEGORY_SUCCEED = 516;
    public static short LOG_CITY_SUCCEED = 517;
    public static short LOG_IMG_DEL = 77;
    public static short LOG_POINT_SUCCEED = 518;
    public static short LOG_TOPIC_SUCCEED = 519;
    public static short LOG_GALLERY_SUCCEED = 520;
    public static short LOG_MENUS_SUCCEED = 521;// 记录菜单日志常量
    public static short LOG_APPGOODS_SUCCEED = 522;// 记录应用商品日志常量
}
