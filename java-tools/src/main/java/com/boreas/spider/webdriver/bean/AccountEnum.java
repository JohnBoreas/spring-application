package com.boreas.spider.webdriver.bean;

/**
 * @author boreas
 * @create 2022-02-10 10:38
 */
public enum AccountEnum {

    ACCOUNT_ENUM(SourceEnums.ali, "", "", "", "", "", ""),
    ;

    private SourceEnums source;
    private String type;
    private String userName;
    private String password;
    private String userId;
    private String ip;
    private String cronexpr;

    private AccountEnum(SourceEnums source, String type, String userName, String password, String userId, String ip, String cronexpr) {
        this.source = source;
        this.type = type;
        this.userName = userName;
        this.password = password;
        this.userId = userId;
        this.ip = ip;
        this.cronexpr = cronexpr;
    }

    public SourceEnums getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public String getIp() {
        return ip;
    }

    public String getCronexpr() {
        return cronexpr;
    }
}
