package com.boreas.spider.webdriver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * webdriver util
 * @author xuhua.jiang
 * @date: 2020年3月25日
 */
public class WebDriverUtil {
	/**
	 * webdriver 截取指定标签图片，并下载
	 * @param type 图片类型
	 * @param ele 获取的元素
	 * @param driver 浏览器
	 * @param screenHotImg 保存路径
	 * @return
	 * @throws IOException
	 */
	public static boolean savePicToPath(String type, WebElement ele, WebDriver driver, String screenHotImg) throws IOException {
		boolean isSave = false;
		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(screenshot);
		// Get the location of element on the page
		Point point = ele.getLocation();
		// Get width and height of the element
		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();
		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		boolean isWrite = ImageIO.write(eleScreenshot, type, screenshot);
		if (isWrite) {
			// Copy the element screenshot to disk
			FileUtils.copyFile(screenshot, new File(screenHotImg));
			isSave = true;
		}
		return isSave;
	}

	/**
	 * 执行一段js
	 * return sessionStorage.getItem(\"user_login_token\");
	 * return localStorage.getItem(\"user_login_token\");
	 * @param webDriver
	 * @param js
	 * @return
	 */
	public static Object executeScriptGetData(WebDriver webDriver, String js) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;// token
		return jsExecutor.executeScript(js);
	}
}
