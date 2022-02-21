package com.boreas.spider.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * webdriver 鼠标动作
 * @date 20210115
 */
public class WebDriverActionsUtil {
    /**
     * Actions action = new Actions(driver);
     * 清单 1. 鼠标左键点击
     * action.click();// 鼠标左键在当前停留的位置做单击操作
     * action.click(driver.findElement(By.name(element)))// 鼠标左键点击指定的元素
     *
     * 清单 2. 鼠标右键点击
     * action.contextClick();// 鼠标右键在当前停留的位置做单击操作
     * action.contextClick(driver.findElement(By.name(element)))// 鼠标右键点击指定的元素
     *
     * 清单 3. 鼠标双击操作
     * action.doubleClick();// 鼠标在当前停留的位置做双击操作
     *
     * 清单 4. 鼠标拖拽动作
     * // 鼠标拖拽动作，将 source 元素拖放到 target 元素的位置。
     * action.dragAndDrop(source,target);
     * // 鼠标拖拽动作，将 source 元素拖放到 (xOffset, yOffset) 位置，其中 xOffset 为横坐标，yOffset 为纵坐标。
     * action.dragAndDrop(source,xOffset,yOffset);
     *
     *清单 5. 鼠标悬停操作
     *  action.clickAndHold();// 鼠标悬停在当前位置，既点击并且不释放
     *  action.clickAndHold(onElement);// 鼠标悬停在 onElement 元素的位置
     * action.clickAndHold(onElement) 这个方法实际上是执行了两个动作，首先是鼠标移动到元素 onElement，然后再 clickAndHold, 所以这个方法也可以写成 action.moveToElement(onElement).clickAndHold()。
     *
     * 清单 6. 鼠标移动操作
     * action.moveToElement(toElement);// 将鼠标移到 toElement 元素中点
     * // 将鼠标移到元素 toElement 的 (xOffset, yOffset) 位置, 这里的 (xOffset, yOffset) 是以元素 toElement 的左上角为 (0,0) 开始的 (x, y) 坐标轴。
     * var cpro_psid ="u2572954"; var cpro_pswidth =966; var cpro_psheight =120;
     * action.moveToElement(toElement,xOffset,yOffset)
     *
     * // 以鼠标当前位置或者 (0,0) 为中心开始移动到 (xOffset, yOffset) 坐标轴
     * action.moveByOffset(xOffset,yOffset);
     * action.moveByOffset(xOffset,yOffset) 这里需要注意，如果 xOffset 为负数，表示横坐标向左移动，yOffset 为负数表示纵坐标向上移动。
     *
     * 清单 7. 鼠标释放操
     * action.release();// 释放鼠标
     */

    /**
     *左键点击元素上的具体坐标位置
     * @param driver
     * @param abnormElement  需要点击的元素
     * @param x 需要点击的元素上的点的X坐标
     * @param y 需要点击的元素上的点的Y坐标
     */
    public static void mouseClick(WebDriver driver, WebElement abnormElement, int x, int y) {
        Actions actions = new Actions(driver);
        actions.release();
        actions.moveToElement(abnormElement, x, y).click().build().perform();
    }

    /**
     *右键点击元素上的具体坐标位置
     * @param driver
     * @param abnormElement  需要点击的元素
     * @param x 需要点击的元素上的点的X坐标
     * @param y 需要点击的元素上的点的Y坐标
     */
    public static void mouseRightClick(WebDriver driver, WebElement abnormElement, int x, int y) {
        Actions actions = new Actions(driver);
        actions.release();
        actions.moveToElement(abnormElement, x, y).contextClick().build().perform();
    }

    /**
     *右键点击元素上的具体坐标位置
     * @param driver
     * @param x 需要点击的元素上的点的X坐标
     * @param y 需要点击的元素上的点的Y坐标
     */
    public static void mouseRightClick(WebDriver driver, int x, int y) {
        Actions actions = new Actions(driver);
        actions.release();
        actions.moveByOffset(x, y).contextClick().build().perform();
    }


    /**
     *鼠标悬停
     * @param driver
     * @param abnormElement  需要点击的元素
     * @param x 需要点击的元素上的点的X坐标
     * @param y 需要点击的元素上的点的Y坐标
     */
    public static void mouseMovetoAndHold(WebDriver driver, WebElement abnormElement, int x, int y){
        Actions actions = new Actions(driver);
        actions.release();
        actions.moveToElement(abnormElement, x, y).clickAndHold().release().build().perform();
    }

    /**
     *拖拽元素上的具体坐标位置
     * @param driver
     * @param abnormElement  需要点击的元素
     * @param x 需要点击的元素上的点的X坐标
     * @param y 需要点击的元素上的点的Y坐标
     */
    public static void mouseMoveto(WebDriver driver, WebElement abnormElement, int x, int y){
        Actions actions = new Actions(driver);
        actions.release();
        actions.moveToElement(abnormElement, x, y).release().build().perform();
    }

    /**
     * 拖拽元素上的具体坐标位置
     * @param driver
     * @param abnormElement  需要点击的元素
     * @param x 需要拖拽元素点的X坐标
     * @param y 需要拖拽元素点的Y坐标
     * @param to_x 拖拽元素点目标位置的X坐标
     * @param to_y 拖拽元素点目标位置的Y坐标
     */
    public static void mouseDragAndDrop(WebDriver driver, WebElement abnormElement, int x, int y, int to_x, int to_y){
        Actions actions = new Actions(driver);
        actions.release();
        actions.moveToElement(abnormElement, x, y).clickAndHold().moveByOffset(to_x,to_y).release().build().perform();
    }
}
