鼠标事件

定义一个action对象

```java
Actions action = new Actions(driver);

1、在某个元素上双击
WebElement element = driver.findElement(By.id（“xx”）);
Actions action = new Actions（driver）；
action.doubleClick(element).bulid().perform();
2、拖拽操作
actions.dragAndDrop(elementsource, elementtarget).perform();
//另一中的拖拽防止dragAndDrop不支持有些浏览器
actions.clickAndHold(elementsource).moveToElement(elementtarget).build().perform();
3、模拟键盘
//普通按键（enter，Backspace,tab,上下左右键)等
actions.sendKeys(Keys.ENTER).perform();
actions.sendKeys(Keys.BACK_SPACE).perform();
//四个修饰键
actions.sendKeys(Keys.CONTROL+"a").perform();

//模拟键盘的操作
/*1、拖拽选择时，使用clickAndHold和moveToElement时，会受到鼠标所在位置的影响，所以尽量是两个方法一起使用；

2、拖拽元素时，dragAndDrop方法，我没有找到实现的场景，所以没有写代码试验；

3、直接按下普通按键时，直接使用sendkeys(theKeys)方法就能够实现；

4、需要使用修饰键时，需要连贯动作中应该使用keydown和keyup方法；

action.keyDown(keys.ATL).sendKeys(keys.F4).keyUp(keys.ALT).perform();//这是不能实现alt+f4关闭窗口的效果的，只能实现单独按f4的效果

5、对于一些快捷键，如ctrl+a，可以直接sendkeys(Keys.CONTROL+"a")
```



```java
## 方法
action.click();
action.click(searchBt);                             //-------单击操作
action.doubleClick().perform();
action.doubleClick(searchBt).perform();   			//-------双击操作
action.clickAndHold().perform();
action.clickAndHold(searchBt).perform();            //-------悬停操作
action.contextClick().perform();
action.contextClick(searchBt).perform();            //-------右击操作
action.dragAndDrop(searchBt, searchBt).perform();   //-------拖拽操作 从一个元素拖拽到目标元素
```

