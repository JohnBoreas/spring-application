package com.boreas.spider.webdriver.bean;

/**
 * @author boreas
 * @create 2022-02-10 10:39
 */
public enum ClazzEnum {

    CLAZZ_ENUM("", "", ""),
    ;

    private String shopName;
    private String adPlanFetch;
    private String loginClass;

    private ClazzEnum(String shopName, String adPlanFetch, String loginClass) {
        this.shopName = shopName;
        this.adPlanFetch = adPlanFetch;
        this.loginClass = loginClass;
    }

    public String getShopName() {
        return shopName;
    }

    public String getAdPlanFetch() {
        return adPlanFetch;
    }

    public String getLoginClass() {
        return loginClass;
    }
}
