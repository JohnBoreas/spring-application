package com.boreas.spider.webdriver;

import com.boreas.spider.webdriver.bean.AccountEnum;
import com.boreas.spider.webdriver.bean.CookieInfo;
import org.openqa.selenium.WebDriver;

/**
 * @author boreas
 * @create 2022-02-10 9:55
 */
public interface ILoginService {
    /**
     * 执行
     * @return
     */
    CookieInfo doExecute();
    /**
     * 滑块
     * @param webDriver
     * @param username
     * @return
     */
    boolean doSlideElement(WebDriver webDriver, String username);
    /**
     * 登录
     * @return
     */
    CookieInfo doLogin(WebDriver webDriver, AccountEnum accountEnum, ILoginDoService service);
    /**
     * 启动webdriver
     * @return
     */
    WebDriver doStartWebDriver();
    /**
     * 填写element
     * @param webDriver
     * @param loginUrl 登录url
     * @param account 账号
     * @param userElt 用户名元素
     * @param userEltType 用户名元素类型
     * @param passElt 密码元素
     * @param passEltType 密码元素类型
     * @return
     */
    boolean doWriterElement(WebDriver webDriver, String loginUrl, AccountEnum account, String userElt, String userEltType, String passElt, String passEltType, ILoginDoService doService);
    /**
     * 验证码识别填写
     * @param webDriver
     * @param account 账号
     * @param yzmElt 验证码元素
     * @param yzmEltType 验证码元素类型
     * @param imgElt 验证码图形元素
     * @param imgEltType 验证码图形元素类型
     * @return
     */
    boolean doWriterYzm(WebDriver webDriver, AccountEnum account, String yzmElt, String yzmEltType, String imgElt, String imgEltType);
    /**
     * 判断是否登录
     * @param webDriver
     * @param loginUrl 完成登录后的url
     * @param loginElt 登录元素
     * @param loginEltType 登录元素类型
     * @return
     */
    boolean isLogin(WebDriver webDriver, String loginUrl, String loginElt, String loginEltType);
    /**
     * 获取cookie
     * @param webDriver
     * @return
     */
    CookieInfo getCookie(WebDriver webDriver, AccountEnum account);
}
