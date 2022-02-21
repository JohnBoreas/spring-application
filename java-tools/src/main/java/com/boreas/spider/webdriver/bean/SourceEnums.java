package com.boreas.spider.webdriver.bean;

/**
 * @author boreas
 * @create 2022-02-10 10:38
 */
public enum SourceEnums {

    ali("", "", 1, ClazzEnum.CLAZZ_ENUM, CodetypeEnum.CO_9008),
    ;

    private String shopName;
    private String site;
    private Integer time;
    private ClazzEnum clazzEnum;
    private CodetypeEnum cost;

    private SourceEnums(String shopName, String site, Integer time, ClazzEnum clazzEnum, CodetypeEnum cost) {
        this.shopName = shopName;
        this.site = site;
        this.time = time;
        this.clazzEnum = clazzEnum;
        this.cost = cost;
    }

    public String getShopName() {
        return shopName;
    }

    public String getSite() {
        return site;
    }

    public Integer getTime() {
        return time;
    }

    public ClazzEnum getClazzEnum() {
        return clazzEnum;
    }

    public CodetypeEnum getCost() {
        return cost;
    }
}
