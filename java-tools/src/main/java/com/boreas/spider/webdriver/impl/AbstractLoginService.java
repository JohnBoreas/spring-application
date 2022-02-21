package com.boreas.spider.webdriver.impl;

import com.boreas.spider.webdriver.ILoginDoService;
import com.boreas.spider.webdriver.ILoginService;
import com.boreas.spider.webdriver.WebDriverUtil;
import com.boreas.spider.webdriver.bean.AccountEnum;
import com.boreas.spider.webdriver.bean.CookieInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author boreas
 * @create 2022-02-10 9:55
 */
public abstract class AbstractLoginService implements ILoginService {

    private static Logger logger = Logger.getLogger(AbstractLoginService.class);

    protected AtomicInteger yzmCount = new AtomicInteger();

    String SCREEN_HOT = "D:\\screenhot";

    protected AccountEnum accountEnum;

    protected String userElt;
    protected String userEltType;
    protected String passElt;
    protected String passEltType;
    protected String yzmElt;
    protected String yzmEltType;
    protected String imgElt;
    protected String imgEltType;
    protected String loginUrl;
    protected String loginElt;
    protected String loginEltType;

    public abstract void intElement();

    public CookieInfo doExecute() {
        intElement();
        WebDriver webDriver = doStartWebDriver();
        CookieInfo cookie = null;
        try {
            int tryCount = 3;
            while (tryCount > 0) {
                cookie = doLogin(webDriver, accountEnum, null);
                if (cookie != null) {
                    break;
                }
                tryCount--;
            }
        } finally {
            // 1. webDriver.Close()   // 关闭当前焦点所在的窗口
            // 2. webDriver.Quit()    // 调用dispose方法, 关闭所有的窗口, 终止对话, 自动删除临时文件夹
            // 3. webDriver.Dispose() // 关闭所有窗口，并且安全关闭session
            //           webDriver.close();
            webDriver.quit();
        }
        if (webDriver != null) {
            //           webDriver.close();
            webDriver.quit();
            webDriver = null;
        }
        return cookie;
    }

    public CookieInfo doLogin(WebDriver webDriver, AccountEnum accountEnum, ILoginDoService service) {
        if (webDriver == null) {
            return null;
        }
        boolean doNext = false;
        boolean isWeihu = false;
        try {
            doNext = doWriterElement(webDriver, accountEnum.getSource().getSite(), accountEnum, userElt, userEltType, passElt, passEltType, service);
            if (doNext) {
                doNext = doWriterYzm(webDriver, accountEnum, yzmElt, yzmEltType, imgElt, imgEltType);
            }
            if (doNext) {//程序自动处理滑块
                doNext = doSlideElement(webDriver, accountEnum.getUserName());
            }
            if (doNext) {
                doNext = isLogin(webDriver, loginUrl, loginElt, loginEltType);
            }
            if (!doNext) {
                doNext = checkOtherInfo(webDriver);
                isWeihu = true;
            }
            if (doNext) {
                CookieInfo cookie = getCookie(webDriver, accountEnum);
                if (isWeihu || StringUtils.isNotEmpty(cookie.getCookie()) || StringUtils.isNotEmpty(cookie.getToken())) {
                    return cookie;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public boolean checkOtherInfo(WebDriver webDriver) throws Exception {
        return false;
    }

    @Override
    public WebDriver doStartWebDriver() {
        logger.info("do start webdriver");
        WebDriver driver = null;
        driver.manage().window().maximize();
        // 页面加载超时时间设置
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        // 定位对象时给Xs 的时间, 如果Xs 内还定位不到则抛出异常
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return driver;
    }

    @Override
    public boolean doWriterElement(WebDriver webDriver, String loginUrl, AccountEnum account, String userElt,
                                   String userEltType, String passElt, String passEltType, ILoginDoService doService) {
        logger.info("do writer element start");
        try {
            // 初始跳转登录页
            String indexURl = loginUrl;
            webDriver.get(indexURl);
            if (doService != null) {
                doService.doExecute();// 做其他点击操作
            }
            // 登录信息填写, 登录
            Thread.sleep(3000);
            WebElement usernameElement = WebDriverUtil.findElement(webDriver, userElt, userEltType);
            if (usernameElement != null) {
                usernameElement.clear();
                usernameElement.sendKeys(account.getUserName());
            }
            Thread.sleep(3000);
            WebElement passwordElement = WebDriverUtil.findElement(webDriver, passElt, passEltType);
            if (passwordElement != null) {
                passwordElement.clear();
                passwordElement.sendKeys(account.getPassword());
            }
            Thread.sleep(3000);
            if (usernameElement != null && passwordElement != null) {
                return true;
            }
        } catch (Exception e) {
            logger.error(account.getUserName() + " writer account info error", e);
        }
        return false;
    }

    @Override
    public boolean doSlideElement(WebDriver webDriver,String username) {
        return true;
    }

    @Override
    public boolean doWriterYzm(WebDriver webDriver, AccountEnum account, String yzmElt, String yzmEltType, String imgElt, String imgEltType) {
        logger.info("do writer yzm start");
        if (StringUtils.isEmpty(yzmElt)) {
            logger.info("no yzm elt, check");
            return true;
        }
        try {
            WebElement ele = WebDriverUtil.findElement(webDriver, imgElt, imgEltType);
            if (ele == null) {
                logger.info("no yzm elt, check");
                return true;
            }
            // 填写验证码
            WebElement yzmTxt = WebDriverUtil.findElement(webDriver, yzmElt, yzmEltType);
            if (yzmTxt != null) {
                yzmTxt.click();
                Thread.sleep(3000);
            }
            // Copy the element screenshot to disk
            Calendar calendar = Calendar.getInstance();
            String screenhotPath = (SCREEN_HOT.endsWith("\\") ? SCREEN_HOT : SCREEN_HOT + "\\") + account.name() + "\\";
            File file = new File(screenhotPath); //如果日志文件夹不存在，创建
            if (!file.exists()) {
                file.mkdirs();
            }
            String screenHotImg = screenhotPath + "\\" + "screen_" + calendar.getTime().getTime() + ".png";
            boolean isSave = WebDriverUtil.savePicToPath("png", ele, webDriver, screenHotImg);
            // 获取验证码
            String yzm = null;
            if (isSave) {
                yzm = null;// 使用超级鹰获取验证码
                yzmCount.incrementAndGet();
                logger.info("验证码使用次数：" + yzmCount.get() + ", " + System.currentTimeMillis());
            }
            if (yzm != null) {
                // 填写验证码
                yzmTxt.clear();
                yzmTxt.sendKeys(yzm);
                Thread.sleep(3000);
                return true;
            }
        } catch (Exception e) {
            logger.error("doWriterYzm " + account.name(), e);
        }
        return false;
    }

    @Override
    public boolean isLogin(WebDriver webDriver, String loginUrl, String loginElt, String loginEltType) {
        logger.info("do check is login start");
        try {
            WebElement yzmBtn = WebDriverUtil.findElement(webDriver, loginElt, loginEltType);
            if (yzmBtn != null) {
                yzmBtn.click();
            }
            Thread.sleep(3000);
            if (webDriver.getCurrentUrl().contains(loginUrl)) {
                return true;
            } else {
                logger.info(webDriver.getCurrentUrl());
            }
        } catch (Exception e) {
            logger.error("isLogin " + loginUrl, e);
        }
        return false;
    }

    @Override
    public CookieInfo getCookie(WebDriver webDriver, AccountEnum account) {
        logger.info("do get cookie start : " + account.name());
        // cookie
        CookieInfo cookieInfo = new CookieInfo();
        Set<Cookie> loginCookies = webDriver.manage().getCookies();// cookie
        String result = null;
        if (loginCookies != null) {
            String cookieStr = "";
            for (Cookie cookie : loginCookies) {
                cookieStr += cookie.getName() + "=" + cookie.getValue() + ";";
            }
            result = cookieStr;
        }
        String token = getToken(webDriver);// token
        cookieInfo.setCookie(result);
        cookieInfo.setToken(token);
        cookieInfo.setLoginStatus(true);
        cookieInfo.setLoginTime(System.currentTimeMillis());
        // 返回cookie
        logger.info("cookie:" + result + ", token : " + token);
        return cookieInfo;
    }

    /**
     * 获取token
     *
     * @param webDriver
     * @return
     */
    public String getToken(WebDriver webDriver) {
        return null;
    }

}
