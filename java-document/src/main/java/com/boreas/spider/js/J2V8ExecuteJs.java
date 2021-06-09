package com.boreas.spider.js;

import com.eclipsesource.v8.V8;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.Set;

public class J2V8ExecuteJs {

	public static void main(String[] args) {
		J2V8ExecuteJs main = new J2V8ExecuteJs();
		try {
			main.WebDriver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jiuxian() {
		// 创建JS代码运行环境
		V8 runtime = V8.createV8Runtime();
		// 执行JS代码，根据返回值类型不同，选择不同的执行方法
		String result = runtime.executeStringScript(
				"var x=\"08@@new@7@try@29@@charCodeAt@return@@Oct@length@false@reverse@@addEventListener@captcha@@@@challenge@9@@substr"
						+ "@charAt@parseInt@@catch@@8@0xFF@@while@search@1540886069@replace@JgSe0upZ@a@@@@@function@setTimeout@@for@Expires"
						+ "@3@Array@fromCharCode@toString@@@Path@@join@createElement@__jsl_clearance@@RegExp@@@var@location@onreadystatechange"
						+ "@Tue@@match@@@@@split@0@href@0xEDB88320@eval@d@@chars@@@@@firstChild@_p@toLowerCase@36@@cookie@1@document@@https@@18"
						+ "@div@hantom@1500@GMT@String@else@window@@54@e@@pathname@f@DOMContentLoaded@innerHTML@@rOm9XFMtA3QKV7nYsPGT4lifyWwkq5vcjH2IdxUoCbhERLaz81DNB6@if"
						+ "@@@@@@@@g@035@@30@@attachEvent@\".replace(/@*$/,\"\").split(\"@\"),y=\"14 7=H(){I('15.1g=15.1N+15.y.A(/[\\?|&]h-l/,\\\\'\\\\')',1E)"
						+ ";1x.1v='W=z.25|1f|'+(H(){14 28=[H(7){9 1i('1G.O('+7+')')},(H(){14 7=1x.V('1C');7.1Q='<C 1g=\\\\'/\\\\'>1U</C>';"
						+ "7=7.1q.1g;14 28=7.19(/1z?:\\/\\//)[1f];7=7.o(28.c).1s();9 H(28){K(14 1U=1f;1U<28.c;1U++){28[1U]=7.p(28[1U])};"
						+ "9 28.U('')}})()],1U=[[[M]+[4],((-~[]<<-~[])+(-~!!1I['1r'+'1D']+[(-~[]<<-~[])]>>(-~[]<<-~[]))+[]+[])+[(+!{})],[4]+[(+!{})]],"
						+ "[(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[])],[[M]+[4],((-~[]<<-~[])+(-~!!1I['1r'+'1D']+[(-~[]<<-~[])]>>(-~[]<<-~[]))+[]+[])+[(+!{})],"
						+ "(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[])+(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[]),[4]+[-~[]],[-~[]]+[(+!{})]+[4],(-~[-~!!1I['1r'+'1D']+"
						+ "[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f])+[4],[-~[]]+[(+!{})]+[-~[]],[m]+(-~[-~!!1I['1r'+'1D']+[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f])"
						+ ",(-~[-~!!1I['1r'+'1D']+[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f])+(-~((-~(+!'')^-~[]))+[]+[[]][1f]),[4]+[-~[]],[M]+[4],((-~[]<<-~[])+"
						+ "(-~!!1I['1r'+'1D']+[(-~[]<<-~[])]>>(-~[]<<-~[]))+[]+[])+[(+!{})],(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[])+(-~[(+!'')-~((-~(+!'')^-~[])"
						+ ")]+[]+[]),(-~[-~!!1I['1r'+'1D']+[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f])+[m],[-~[]]+[-~[]]+(-~[]-~[]+[]),(-~[(+!'')-~((-~(+!'')^-~[]"
						+ "))]+[]+[])+(-~[-~!!1I['1r'+'1D']+[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f]),(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[])+(-~[-~!!1I['1r'+'1D']"
						+ "+[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f]),[-~[]]+[-~[]]+(-~[]-~[]+[]),(-~[-~!!1I['1r'+'1D']+[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f])+("
						+ "-~[]-~[]+[]),[-~[]]+(-~[]-~[]+[])+[-~[]],[-~[]]+[-~[]]+(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[]),(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[])+[m]"
						+ ",[-~[]]+[-~[]]+(-~((-~(+!'')^-~[]))+[]+[[]][1f]),[-~[]]+[(+!{})]+[4]],[[4]],[[-~[]]+[(+!{})]+[M],[4]+(-~((-~(+!'')^-~[]))+[]+[[]][1f])"
						+ ",[-~[]]+(-~[]-~[]+[])+[-~[]],(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[])+((-~[]<<-~[])+(-~!!1I['1r'+'1D']+[(-~[]<<-~[])]>>(-~[]<<-~[]))+[]+[]),"
						+ "[M]+[4],((-~[]<<-~[])+(-~!!1I['1r'+'1D']+[(-~[]<<-~[])]>>(-~[]<<-~[]))+[]+[])+[-~[]],(-~[(+!'')-~((-~(+!'')^-~[]))]+[]+[])+(-~[-~!!1I['1r'"
						+ "+'1D']+[-~(+!'')]*(-~[-~[]-~[]])]+[]+[[]][1f])]];K(14 7=1f;7<1U.c;7++){1U[7]=28.e()[[-~[]]](1U[7])};9 1U.U('')})()+';L=17, 27-b-1B 1:1K:6 1F"
						+ ";S=/;'};1T((H(){5{9 !!1I.g;}s(1L){9 d;}})()){1x.g('1P',7,d)}1H{1x.29('16',7)}\",f=function(x,y){var a=0,b=0,c=0;x=x.split(\"\");y=y||99;"
						+ "while((a=x.shift())&&(b=a.charCodeAt(0)-77.5))c=(Math.abs(b)<13?(b+48.5):parseInt(a,36))+y*c;return c},z=f(y.match(/\\w/g).sort(function(x,"
						+ "y){return f(x)-f(y)}).pop());while(z++)try{eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}));break}catch(_){}"
								.trim().replace("<script>", "").replace("</script>", "")
								.replace("eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}))",
										"y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)})"));
		System.out.println(result);
		result=result.substring(result.indexOf("var _7"),result.indexOf("if((function(){try{return") -1);
		// 第二次处理JS并执行
		System.out.println(result);
		String __jsl_clearance = runtime.executeStringScript(result);
		System.out.println(__jsl_clearance);
		// 释放运行环境
		runtime.release();
	}

	public void WebDriver() {
		// 写法一：
		HtmlUnitDriver driver = new HtmlUnitDriver();
		// 必须设置为true,才能执行js代码
		driver.setJavascriptEnabled(false);
		driver.get("https://list.tmall.com/search_product.htm?tbpm=1&spm=a3204.7084713.1996500281.55.waO88a&user_id=725677994&cat=51440019&active=1&acm=lb-zebra-26901-351264.1003.4.468294&style=g&sort=s&scm=1003.4.lb-zebra-26901-351264.OTHER_14440874222721_468294&industryCatId=51462017&s=0");
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie);
		}
		driver.setJavascriptEnabled(false);
		driver.get("http://www.jiuxian.com/goods-55159.html");
		String pageSource = driver.getPageSource();
		System.out.println(pageSource);
		
		// 写法二：
		System.getProperties().setProperty("webdriver.chrome.driver", "D:\\软件\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		WebDriver driver2 = new ChromeDriver(options);
		//随便打开一个网站
		driver.get("http://www.jiuxian.com/goods-55159.html");
		Set<Cookie> cookies2 = driver2.manage().getCookies();
		for (Cookie cookie : cookies2) {
			System.out.println(cookie);
		}
		driver.quit();
	}

	public void J2V8() throws ParseException, IOException, NoSuchMethodException, ScriptException {
		String url = "http://www.jiuxian.com/goods-55159.html";
		CloseableHttpClient client = HttpClients.createDefault();
		// 设置代理
		//HttpHost proxy = new HttpHost("192.167.5.13", 808, "http");
		RequestConfig config = RequestConfig.custom().build();
		HttpGet get = new HttpGet(url);
		// 模拟浏览器
		get.setConfig(config);
//		get.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		get.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//		get.setHeader("Accept-Encoding", "gzip, deflate");
//		get.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//		get.setHeader("Connection", "keep-alive");
//		get.setHeader("Host", "www.cnvd.org.cn");
//		get.setHeader("referer", "http://www.cnvd.org.cn/");
		get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
		CloseableHttpResponse response = client.execute(get);
		// 拿到第一次请求返回的JS
		if (response.getStatusLine().getStatusCode() == 521) {
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity, "utf-8");
			System.out.println(html);
			// 处理从服务器返回的JS，并执行
			String resHtml = "function getClearance(){" + html.trim() + "};";
		    resHtml = resHtml.replace("</script>", "");
		    resHtml = resHtml.replace("eval(y", "return(y");
		    resHtml = resHtml.replace("<script>", "");
			ScriptEngineManager manager = new ScriptEngineManager();
		    ScriptEngine engine = manager.getEngineByName("js");
		    engine.eval(resHtml);
		    Invocable invocable = (Invocable) engine;
		    String result = (String) invocable.invokeFunction("getClearance");
// j2v8处理
//			String js = html.trim().replace("<script>", "").replace("</script>", "").replace("eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}))",
//					"y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)})");
//			V8 runtime = V8.createV8Runtime();
//			String result = runtime.executeStringScript(js);
		    
		    
			System.out.println(result);
			String __jsl_clearance = chrome(result);
			//executejs(result);
			// 第二次处理JS并执行
//			result = "function getClearance2(){var a" + result.substring(result.indexOf("document.cookie") + 15, result.indexOf("Path=/;'")) + "Path=/;';return a;};";
//			System.out.println(result);
//			String __jsl_clearance = runtime.executeStringScript(result);
//			System.out.println(__jsl_clearance);
//			runtime.release();
			get.setHeader("Cookie", __jsl_clearance);
			response = client.execute(get);
		}
		// 拿到最终想要的页面
		HttpEntity entity = response.getEntity();
		String res = EntityUtils.toString(entity, "utf-8");
		System.out.println(res);

	}

	public String executejs(String resJs) throws ScriptException, NoSuchMethodException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
	    String overJs = "function getCookie(){ var a" + resJs.split("document.cookie")[1].split("Path=/;'")[0]+"Path=/;';return a;};";
	    overJs = overJs.replace("document.createElement('div')", "'<div></div>'");
	    System.out.println(overJs);
	    engine.eval(overJs);
	    Invocable invocable2 = (Invocable) engine;
	    String over = (String) invocable2.invokeFunction("getCookie");
	    System.out.println(over);
	    return over;
	}
	/**
	 * @Description: 存在document用webdriver来做
	 * @param resJs
	 * @return
	 */
	public String chrome(String resJs) {
	    //selenium 模拟
	    System.getProperties().setProperty("webdriver.chrome.driver", "D:\\软件\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		WebDriver driver = new ChromeDriver(options);
		//随便打开一个网站
		driver.get("https://www.baidu.com");
		//让浏览器去执行js 我们拿到return cookie的值
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String s = "var a" + resJs.substring(resJs.indexOf("document.cookie") + 15, resJs.indexOf("Path=/;'")) + "Path=/;';return a;";
//		s = s.replace("return return", "return eval");
		System.out.println(s);
		String cookie=(String)js.executeScript(s);
	    System.out.println(cookie);
	    driver.quit();
	    return cookie;
	}
}
