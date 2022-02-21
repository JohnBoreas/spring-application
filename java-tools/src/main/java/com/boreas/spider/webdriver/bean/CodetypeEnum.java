package com.boreas.spider.webdriver.bean;

/**
 * @author boreas
 * @create 2022-02-10 10:48
 */
public enum CodetypeEnum {

    CO_9008(9008, "坐标多选,返回5~8个坐标,如:x1,y1|x2,y2|x3,y3|x4,y4|x5,y5", "40");

    private Integer type;
    private String info;
    private String price;

    private CodetypeEnum(Integer type, String info, String price) {
        this.type = type;
        this.info = info;
        this.price = price;
    }

    public Integer getType() {
        return this.type;
    }

    public String getInfo() {
        return this.info;
    }

    public String getPrice() {
        return this.price;
    }
}
