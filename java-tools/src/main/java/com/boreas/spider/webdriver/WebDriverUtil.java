package com.boreas.spider.webdriver;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * webdriver util
 * @date: 2020年3月25日
 */
public class WebDriverUtil {

    private static Logger logger = Logger.getLogger(WebDriverUtil.class);

    /**
     * webdriver 截取指定标签图片，并下载
     * @param type         图片类型
     * @param ele          获取的元素
     * @param driver       浏览器
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

    /**
     * 根据指定的元素类型获取WebElement
     * @param webDriver
     * @param elt 元素名称
     * @param eltType 元素类型
     * @return
     */
    public static WebElement findElement(WebDriver webDriver, String elt, String eltType) {
        if (StringUtils.isEmpty(elt) || StringUtils.isEmpty(eltType)) {
            logger.info("element is empty, please check again");
            return null;
        }
        try {
            if ("id".equals(eltType)) {
                return webDriver.findElement(By.id(elt));
            }
            if ("class".equals(eltType)) {
                return webDriver.findElement(By.className(elt));
            }
            if ("name".equals(eltType)) {
                return webDriver.findElement(By.name(elt));
            }
            if ("tag".equals(eltType)) {
                return webDriver.findElement(By.tagName(elt));
            }
        } catch (Exception e) {
            logger.error(elt + " + " + eltType, e);
        }
        logger.info("no support this element :" + elt + ":" + eltType);
        return null;
    }

    /**
     * 根据指定的元素类型获取list WebElements
     * @param webElement
     * @param elt 元素名称
     * @param eltType 元素类型
     * @return
     */
    public static List<WebElement> findElements(WebElement webElement, String elt, String eltType) {
        if (StringUtils.isEmpty(elt) || StringUtils.isEmpty(eltType)) {
            logger.info("element is empty, please check again");
            return null;
        }
        try {
            if ("class".equals(eltType)) {
                return webElement.findElements(By.className(elt));
            } else if ("name".equals(eltType)) {
                return webElement.findElements(By.name(elt));
            } else if ("tag".equals(eltType)) {
                return webElement.findElements(By.tagName(elt));
            }
        } catch (Exception e) {
            logger.error(elt + " + " + eltType, e);
        }
        logger.info("no support this element :" + elt + ":" + eltType);
        return null;
    }
}
