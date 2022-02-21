package com.boreas.spider.webdriver.bean;

/**
 * @author boreas
 * @create 2022-02-10 9:56
 */
public class CookieInfo {

    private String cookie;
    private String token;
    private Long loginTime;// cookie 抓取时间
    private Boolean loginStatus;// cookie 状态
    private String proxyIpPort;
    public String getCookie() {
        return cookie;
    }
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
    public Boolean getLoginStatus() {
        return loginStatus;
    }
    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }
    public String getProxyIpPort() {
        return proxyIpPort;
    }
    public void setProxyIpPort(String proxyIpPort) {
        this.proxyIpPort = proxyIpPort;
    }
}
