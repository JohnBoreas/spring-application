1、获取cookie、token等信息

```java
// 获取cookie方法
Set<Cookie> loginCookies = webDriver.manage().getCookies();// cookie

// 获取token的方法：
return sessionStorage.getItem(\"user_login_token\");
return localStorage.getItem(\"user_login_token\");
public static Object executeScriptGetData(WebDriver webDriver, String js) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;// token
		return jsExecutor.executeScript(js);
}
1、要从Local Storage中获取还是要从Session Storage中获取，具体看目标系统存到哪个中-----开发者模式查看
2、window.SessionStorage和直接写SessionStorage是等效的
3、一定要使用return，不然获取到的一直是None
4、get的Item不一定就叫token，得具体看目标系统把token存到哪个变量中
```



2、webdriver反爬虫

window.navigator.webdriver

在非selenium环境下其值为undefined，而在selenium环境下，其值为true

```java
// 在启动Chromedriver之前，为Chrome开启实验性功能参数(防爬虫)
// https://www.cnblogs.com/xieqiankun/p/hide-webdriver.html
options.setExperimentalOption("excludeSwitches", new Object[]{"enable-automation"});
driver = new ChromeDriver(options);
```



3、webdriver打开已有浏览器

```java
1、谷歌浏览器路径下打开cmd，执行以下代码。
chrome.exe --remote-debugging-port=9527 --user-data-dir="C:\selenum\chrome_userdata"
 
2、代码中加入
options = Options()
options.add_experimental_option("debuggerAddress", "127.0.0.1:9527")
bro = webdriver.Chrome(options=options)    

ChromeOptions options = new ChromeOptions();
options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222"); //(1)
```

