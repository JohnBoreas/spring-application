package com.boreas.spider.webdriver;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestWebDriver {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", TestWebDriver.class.getClassLoader().getResource("").getPath() + "webDriver/chromedriver.70.0.3538.9.exe");// chromedriver服务地址
		System.setProperty("webdriver.chrome.bin", "C://Users//xuhua.jiang//AppData//Local//Google//Chrome//Application");
		WebDriver driver = new ChromeDriver();
		// 打开百度
		driver.get("https://login.m.taobao.com/login.htm");
		// 搜索框输入鹿晗
		driver.findElement(By.id("username")).sendKeys("");
		driver.findElement(By.id("password")).sendKeys("");
		driver.findElement(By.id("btn-submit")).click();
		driver.manage().getCookies();
        Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
        System.out.println("Size: " + cookies.size());
	}
	
	public void test() throws FileNotFoundException, IOException, InterruptedException {
		// 初始化参数据
        System.setProperty("webdriver.gecko.driver", "");
        FirefoxDriver driver = new FirefoxDriver();
        String baseUrl = "https://passport.csdn.net/account/login?ref=toolbar";
        // 加载url
        driver.get(baseUrl);
        // 等待加载完成
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // 获取页面元素
        WebElement elemUsername = driver.findElement(By.name("username"));
        WebElement elemPassword = driver.findElement(By.name("password"));
        WebElement btn = driver.findElement(By.className("logging"));
        WebElement rememberMe = driver.findElement(By.id("rememberMe"));
        // 操作页面元素
        elemUsername.sendKeys("");
        elemPassword.sendKeys("");
        rememberMe.click();
        // 提交表单
        btn.submit();
        Thread.sleep(5000);
        driver.get("http://msg.csdn.net/");
        Thread.sleep(5000);
        // 获取cookies
        driver.manage().getCookies();
        Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
        System.out.println("Size: " + cookies.size());
        Iterator<org.openqa.selenium.Cookie> itr = cookies.iterator();

        CookieStore cookieStore = new BasicCookieStore();

        while (itr.hasNext()) {
            Cookie cookie = itr.next();
            BasicClientCookie bcco = new BasicClientCookie(cookie.getName(), cookie.getValue());
            bcco.setDomain(cookie.getDomain());
            bcco.setPath(cookie.getPath());
            cookieStore.addCookie(bcco);
        }
        // 保存到文件
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("")));
        oos.writeObject(cookieStore);
        oos.close();
	}
}
