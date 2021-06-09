package com.boreas.spider.httpclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description: HttpClient
 * @author xuhua.jiang
 * @date: 2018年7月16日
 */
public class HttpClientDemo {

	public HttpClientDemo() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws IOException {

		boolean flag = false;
		do {			
			System.out.println(flag);
		} while (flag);
		//HttpClientDemo.baseTest("http://www.cnblogs.com");
		
		//HttpClientDemo.headerTest("http://www.tuicool.com");
		
		//HttpClientDemo.pictureTest("https://wx2.sinaimg.cn/mw690/006RYJvjly1fmfk7c049vj30zk0qogq6.jpg");
		HttpClientDemo demo = new HttpClientDemo();
		baseTest("https://www.finishline.com");
		//HttpClientDemo.pictureTest("http://www.tuicool.com");
		//HttpClientDemo.proxyTest("http://acs.m.taobao.com/h5/mtop.taobao.tceget.steins.qingdan.xget/1.0/?appKey=12574478&t=1535446255810&sign=1d7f8eedeac1913acbe350ae87d3b592&AntiCreep=true&api=mtop.taobao.tceget.steins.qingdan.xget&v=1.0&dataType=jsonp&timeout=20000&H5Request=true&type=jsonp&callback=mtopjsonp2&data=%7B%22d%22%3A%22%7B%5C%22tce_sid%5C%22%3A%5C%221891397%5C%22%2C%5C%22tce_vid%5C%22%3A%5C%220%5C%22%2C%5C%22tid%5C%22%3A%5C%22%5C%22%2C%5C%22tab%5C%22%3A%5C%221%5C%22%2C%5C%22topic%5C%22%3A%5C%2253362_12015%5C%22%2C%5C%22count%5C%22%3A%5C%22%5C%22%2C%5C%22pageSize%5C%22%3A%5C%226%5C%22%2C%5C%22pageNo%5C%22%3A%5C%221%5C%22%2C%5C%22env%5C%22%3A%5C%22online%5C%22%2C%5C%22psId%5C%22%3A%5C%2253362%5C%22%2C%5C%22currentPage%5C%22%3A%5C%221%5C%22%2C%5C%22sceneId%5C%22%3A%5C%2212015%5C%22%2C%5C%22src%5C%22%3A%5C%22phone%5C%22%7D%22%7D");
	}

	/**
	 * 抓取网页信息使用get请求
	 * HTTP方法的：GET，HEAD， POST，PUT，DELETE， TRACE和OPTIONS。
	 * 为每个方法类型：一个特定的类HttpGet， HttpHead，HttpPost， HttpPut，HttpDelete， HttpTrace，和HttpOptions。
	 * @param args
	 * @throws IOException
	 */
	public static void baseTest(String url) throws IOException {
		// 创建httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建httpGet实例
		HttpGet httpGet = new HttpGet(url);
		// Manager获取Connection超时时间、
		HttpHost proxy = new HttpHost("10.0.12.108", 31280);
		RequestConfig requestConfig = RequestConfig.custom()
				.setProxy(proxy)
				.setConnectTimeout(10000)
				.setSocketTimeout(10000)
				.setConnectionRequestTimeout(3000)
				.build();
		httpGet.setConfig(requestConfig);
//		httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.9 Safari/537.36");
//		httpGet.addHeader("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
//		httpGet.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		httpGet.addHeader("accept-encoding", "gzip, deflate, br");
//		httpGet.addHeader("cache-control", "no-cache");
//		httpGet.addHeader("pragma", "no-cache");
//		httpGet.addHeader("upgrade-insecure-requests", "1");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		if (response != null) {
			HttpEntity entity = response.getEntity(); // 获取网页内容
			// 推荐方法是通过使用其 HttpEntity#getContent()或 HttpEntity#writeTo(OutputStream)方法
			// EntityUtils强烈建议不要使用，除非响应实体源自可信HTTP服务器并且已知长度有限
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + ":" + header.getValue());
			}
			String result = EntityUtils.toString(entity, "UTF-8");
			System.out.println("网页内容:/n" + result);
		}
		if (response != null) {
			response.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}
	
	/**
     * 抓取网页信息使用get请求
     * @param args
     * @throws IOException
     */
    public static void headerTest(String url) throws IOException {
        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpGet实例
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response != null){
        	Header[] headers = response.getAllHeaders();
        	for (Header header : headers) {
				System.out.println(header.getName() + ":" + header.getValue());
			}
        	
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("响应状态:" + statusCode);

            HttpEntity entity =  response.getEntity();  //获取网页内容
            //System.out.println("Content-Encoding" + entity.getContentEncoding().getValue());
            //　Content-Type来提取我们需要爬取的网页或者是爬取的时候，需要过滤掉的一些网页。
            System.out.println("Content-Type:" + entity.getContentType().getValue());
            System.out.println("ContentLength:" + entity.getContentLength());
            
            
            String result = EntityUtils.toString(entity, "UTF-8");
            //System.out.println("网页内容:"+result);
        }
        if (response != null){
            response.close();
        }
        if (httpClient != null){
            httpClient.close();
        }
    }
    /**
     * @Description: picture
     * @author: xuhua.jiang
     * @date: 2018年7月16日
     *
     * @param url
     * @throws IOException
     */
	public static void pictureTest(String url) throws IOException {
		// 图片路径
		// String url = ;
		// 创建httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建httpGet实例
		HttpGet httpGet = new HttpGet(url);
		// 设置请求头消息
		httpGet.setHeader("user-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		// 获取.后缀
		String fileName = url.substring(url.lastIndexOf("."), url.length());

		if (response != null) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				System.out.println("Content-Type:" + entity.getContentType().getValue());
				InputStream inputStream = entity.getContent();
				// 文件复制
				FileUtils.copyToFile(inputStream, new File("D:\\love" + fileName));
			}
		}
		if (response != null) {
			response.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}
	
	/**
	 * 
	 * @Description: proxy
	 * @author: xuhua.jiang
	 * @date: 2018年7月16日
	 *
	 * @param url
	 * @throws IOException
	 */
	public static void proxyTest(String url) throws IOException {
		// 创建httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建httpGet实例
		HttpGet httpGet = new HttpGet(url);
		// 设置代理IP，设置连接超时时间 、 设置 请求读取数据的超时时间 、 设置从connect
		// Manager获取Connection超时时间、
		HttpHost proxy = new HttpHost("10.0.12.100", 31280);
		RequestConfig requestConfig = RequestConfig.custom()
				.setProxy(proxy)
				.setConnectTimeout(10000)
				.setSocketTimeout(10000).setCookieSpec("cna=I3i8E0OnCxkCAXTkixocOIeO; t=b4466faabb47bcc675c557bbb565c070; WAPFDFDTGFG=%2B4cMKKP%2B8PI%2BtSs9DYkmeL2VvBZT6M4%3D; _w_app_lg=23; thw=cn; miid=881467498743415506; hng=CN%7Czh-CN%7CCNY%7C156; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; UM_distinctid=164c64d3eb9460-0ed74a85b2c6af-3c3c520d-1fa400-164c64d3eba2ad; tg=0; l=At3d6wt3JtvzpU7H8S9SOO7LbbLX-hFM; tracknick=%5Cu98CE%5Cu4E4B%5Cu65ED%5Cu8679; lgc=%5Cu98CE%5Cu4E4B%5Cu65ED%5Cu8679; cookie2=3e21cb9c28a0922fb7043f8495f286ec; _tb_token_=e3e5e73b8a533; whl=-1%260%260%261535076589418; _m_unitapi_v_=1508566261407; mt=ci=2_1&np=; tkmb=e=bG1nrX4sId2bg0u6X7YUtSmMSYJn1e96jRxqvKHfXNjhKbFWhHKXr4YCcm_VoFIltq6iquKE38pC0JZdGKfhw644MRH8Nq9UVykoN0xkOT8ddagXJ2WNb7ak480tdX3nxgYB5MKlmOVYKdObAOOCZYolYnKKv5OXahOeZxDaYYaergyyzE8eB1qv6VXvBkcMZz0TOoLc3U5NNgFC-FlWIavBLUh73LZMp7niwwfbUeMH25EV3s1TrrMPgKF6n_hX2ddndr--RTJYKdObAOOCZeHwcZgXMcs3eXX72mp250XDG_1N5hlzNg&iv=0&et=1535349913&tk_cps_param=32293866&tkFlag=0; munb=1100157369; ntm=0; _m_user_unitinfo_=center; _m_h5_tk=001807593fa34d46c0f915909c4bc357_1535455955767; _m_h5_tk_enc=9d4bb2831d4d64d9b862ab375f678add; v=0; ockeqeudmj=l%2BnquUQ%3D; unb=1100157369; sg=%E8%99%B99b; _l_g_=Ug%3D%3D; skt=6fc0f3062b6d425e; uc1=cookie21=W5iHLLyFe3xm&cookie15=WqG3DMC9VAQiUQ%3D%3D&cookie14=UoTfLinj2CNa3w%3D%3D; cookie1=VvqgUooFWHlo684bEYDbJM1EI0SKopmVy1IK1CI88s0%3D; csg=23d4fa7b; uc3=vt3=F8dBzrSNpiAl9KpFPC8%3D&id2=UoCJiFbMT3j3LQ%3D%3D&nk2=1CAkb5Or4WM%3D&lg2=V32FPkk%2Fw0dUvg%3D%3D; _cc_=VFC%2FuZ9ajQ%3D%3D; dnk=%5Cu98CE%5Cu4E4B%5Cu65ED%5Cu8679; _nk_=%5Cu98CE%5Cu4E4B%5Cu65ED%5Cu8679; cookie17=UoCJiFbMT3j3LQ%3D%3D; isg=BJSURACls8K_sCe-VEaXUgJrZdIMJbcGdwHDMS51Jp-jGTJjVvwBZ1PbHReB4fAv")
				.setConnectionRequestTimeout(3000)
				.build();
		httpGet.setConfig(requestConfig);
		// 设置请求头消息
		
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Host", "acs.m.taobao.com");
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader("Referer", "http://market.m.taobao.com/apps/market/qingdan/list-2018.html");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Mobile Safari/537.36");
        
		CloseableHttpResponse response = httpClient.execute(httpGet);

		if (response != null) {
			HttpEntity entity = response.getEntity(); // 获取返回实体
			if (entity != null) {
				System.out.println("网页内容为:" + EntityUtils.toString(entity, "utf-8"));
			}
		}
		// 为了确保正确释放系统资源，必须关闭与实体关联的内容流或响应本身
		if (response != null) {
			response.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}
	
	
	public void httpResponseTest() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		response.addHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
		
		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		
		Header h1 = response.getFirstHeader("Set-Cookie");
		System.out.println(h1);
		Header h2 = response.getLastHeader("Set-Cookie");
		System.out.println(h2);
		
		Header[] hs = response.getHeaders("Set-Cookie");
		System.out.println(hs.length);
		
		HeaderIterator it = response.headerIterator("Set-Cookie");
		while (it.hasNext()) {
		    System.out.println(it.next());
		}
		// 获取header里面的内容
		HeaderElementIterator element = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));
		while (it.hasNext()) {
		    HeaderElement elem = element.nextElement();
		    System.out.println(elem.getName() + " = " + elem.getValue());
		    NameValuePair[] params = elem.getParameters();
		    for (int i = 0; i < params.length; i++) {
		        System.out.println(" " + params[i]);
		    }
		}
		
		
	}
	
	public void entityTest() throws ParseException, IOException {
		StringEntity myEntity = new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));
		
		System.out.println(myEntity.getContentType());
		System.out.println(myEntity.getContentLength());
		System.out.println(EntityUtils.toString(myEntity));
		System.out.println(EntityUtils.toByteArray(myEntity).length);
		// Entity
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost/");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
		    HttpEntity entity = response.getEntity();
		    if (entity != null) {
		    	// 写法1：不建议用EntityUtils.toString(entity),除非响应实体源自可信HTTP服务器并且已知长度有限。
		        System.out.println(EntityUtils.toString(entity));
		        // 写法2：InputStream
		        InputStream instream = entity.getContent();
		        int byteOne = instream.read();
		        // 写法3：writeTo(OutputStream )
		        entity.writeTo(new OutputStream() {					
					@Override
					public void write(int b) throws IOException {
						// TODO Auto-generated method stub
					}
				});
		    }
		} finally {
		    response.close(); // 当CloseableHttpClient不再需要实例并且即将超出范围时，必须通过调用该CloseableHttpClient#close() 方法来关闭与其关联的连接管理器。
		}
		
		/**
		 *  建议让HttpClient根据要传输的HTTP消息的属性选择最合适的传输编码。但是，可以通过设置HttpEntity#setChunked()为true 来通知HttpClient优先使用块编码。
		 *  请注意，HttpClient将仅使用此标志作为提示。使用不支持块编码的HTTP协议版本（例如HTTP / 1.0）时，将忽略此值。
		 */
		StringEntity entity = new StringEntity("important message", ContentType.create("plain/text", Consts.UTF_8));
		entity.setChunked(true);
		HttpPost httppost = new HttpPost("http://localhost/acrtion.do");
		httppost.setEntity(entity);
		
		
		/**
		 *  HttpClient提供了几个类，可用于通过HTTP连接有效地流出内容。这些类的实例可以与实体包围请求，如相关联POST并PUT 以包围实体内容分成传出HTTP请求。
		 *  HttpClient的提供了几个类为最常见的数据的容器，如字符串，字节数组，输入流，和文件：StringEntity， ByteArrayEntity， InputStreamEntity，和 FileEntity。
		 */
		File file = new File("somefile.txt");
		FileEntity entity0 = new FileEntity(file, ContentType.create("text/plain", "UTF-8"));
		HttpPost httppost0 = new HttpPost("http://localhost/action.do");
		httppost0.setEntity(entity0);
	}
	
	
	public void handleResponseTest() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost/json");

		ResponseHandler<Object> rh = new ResponseHandler<Object>() {

		    @Override
		    public JsonObject handleResponse(
		            final HttpResponse response) throws IOException {
		        StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        if (statusLine.getStatusCode() >= 300) {
		            throw new HttpResponseException(
		                    statusLine.getStatusCode(),
		                    statusLine.getReasonPhrase());
		        }
		        if (entity == null) {
		            throw new ClientProtocolException("Response contains no content");
		        }
		        Gson gson = new GsonBuilder().create();
		        ContentType contentType = ContentType.getOrDefault(entity);
		        Charset charset = contentType.getCharset();
		        Reader reader = new InputStreamReader(entity.getContent(), charset);
		        return (JsonObject) gson.fromJson(reader, Object.class);
		    }
		};
		Object myjson = client.execute(httpget, rh);
		
		
		ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepAlive = super.getKeepAliveDuration(response, context);
				if (keepAlive == -1) {
					// Keep connections alive 5 seconds if a keep-alive value
					// has not be explicitly set by the server
					keepAlive = 5000;
				}
				return keepAlive;
			}

		};
		CloseableHttpClient httpclient = HttpClients.custom().setKeepAliveStrategy(keepAliveStrat).build();
	}
	
	public void testHttpClient() throws ClientProtocolException, IOException {
		/**
		 * HttpClient线程安全:HttpClient实现预计是线程安全的。建议将此类的同一实例重用于多个请求执行。
		 * 
		 * HttpClient资源释放:当CloseableHttpClient不再需要实例并且即将超出范围时，必须通过调用该CloseableHttpClient#close() 方法来关闭与其关联的连接管理器
		 * 
		 * 在HTTP请求执行过程中，HttpClient将以下属性添加到执行上下文中：
		 * 【1】HttpConnection 表示与目标服务器的实际连接的实例。
		 * 【2】HttpHost 表示连接目标的实例。
		 * 【3】HttpRoute 表示完整连接路由的实例
		 * 【4】HttpRequest表示实际HTTP请求的实例。执行上下文中的最终HttpRequest对象始终表示消息的状态，与 发送到目标服务器的状态完全相同。
		 *     默认HTTP / 1.0和HTTP / 1.1使用相对请求URI。但是，如果请求是通过代理以非隧道模式发送的，那么URI将是绝对的。
		 * 【5】HttpResponse 表示实际HTTP响应的实例。
		 * 【6】java.lang.Boolean 表示实际请求是否已完全发送到连接目标的标志的对象。
		 * 【7】RequestConfig 表示实际请求配置的对象。
		 * 【8】java.util.List<URI> 表示在请求执行过程中收到的所有重定向位置的集合的对象。
		 */
		// 可以使用HttpClientContext适配器类来简化与上下文状态的交互。
		HttpContext context = null;
		HttpClientContext clientContext = HttpClientContext.adapt(context);
		HttpHost target = clientContext.getTargetHost();
		HttpRequest request = clientContext.getRequest();
		HttpResponse response = clientContext.getResponse();
		RequestConfig config = clientContext.getRequestConfig();
		// 初始请求设置的请求配置将保留在执行上下文中，并传播到共享相同上下文的连续请求
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(1000)
		        .setConnectTimeout(1000)
		        .build();

		HttpGet httpget1 = new HttpGet("http://localhost/1");
		httpget1.setConfig(requestConfig);
		CloseableHttpResponse response1 = httpclient.execute(httpget1, context);
		try {
		    HttpEntity entity1 = response1.getEntity();
		} finally {
		    response1.close();
		}
		HttpGet httpget2 = new HttpGet("http://localhost/2");
		CloseableHttpResponse response2 = httpclient.execute(httpget2, context);
		try {
		    HttpEntity entity2 = response2.getEntity();
		    entity2.isChunked();
		} finally {
		    response2.close();
		}
		
	}
	/**
	 * 异常处理：
	 * HTTP协议处理器可以抛出两种类型的异常： java.io.IOException在I / O故障的情况下，例如套接字超时或套接字重置，并HttpException发出HTTP故障，例如违反HTTP协议。
	 * 通常，I / O错误被认为是非致命和可恢复的，而HTTP协议错误被认为是致命的，无法自动恢复。
	 * 请注意，HttpClient 实现重新抛出HttpExceptions as ClientProtocolException，它是的子类java.io.IOException。这使用户能够HttpClient从单个catch子句处理I / O错误和协议违规。
	 * 
	 * HTTP协议不适合所有类型的应用程序。HTTP是一种简单的面向请求/响应的协议，最初设计用于支持静态或动态生成的内容检索。它从未打算支持事务操作。
	 * 
	 * 自动异常恢复
	 * 默认情况下，HttpClient会尝试从I / O异常中自动恢复。默认的自动恢复机制仅限于一些已知安全的例外情况。
	 * HttpClient不会尝试从任何逻辑或HTTP协议错误（从HttpException类派生的错误）中恢复 。
	 * HttpClient将自动重试那些被认为是幂等的方法。
	 * 当HTTP请求仍然传输到目标服务器时（即请求尚未完全传输到服务器），HttpClient将自动重试那些因传输异常而失败的方法。
	 */
	public void testRequestRetryHandler() {
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
		    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
		        if (executionCount >= 5) {
		            // Do not retry if over max retry count
		            return false;
		        }
		        if (exception instanceof InterruptedIOException) {
		            // Timeout
		            return false;
		        }
		        if (exception instanceof UnknownHostException) {
		            // Unknown host
		            return false;
		        }
		        if (exception instanceof ConnectTimeoutException) {
		            // Connection refused
		            return false;
		        }
		        if (exception instanceof SSLException) {
		            // SSL handshake exception
		            return false;
		        }
		        HttpClientContext clientContext = HttpClientContext.adapt(context);
		        HttpRequest request = clientContext.getRequest();
		        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
		        if (idempotent) {
		            // Retry if the request is considered idempotent
		            return true;
		        }
		        return false;
		    }

		};
		CloseableHttpClient httpclient = HttpClients.custom().setRetryHandler(myRetryHandler).build();
	}
	
	/**
	 * 重定向处理:
	 * HttpClient自动处理所有类型的重定向，除了HTTP规范明确禁止的需要用户干预的重定向。
	 * See Other（状态代码303）重定向，POST并 根据HTTP规范的要求PUT将GET请求转换为请求。
	 * 可以使用自定义重定向策略来放宽对HTTP规范强加的POST方法的自动重定向的限制。
	 * 
	 * HttpClient通常必须在执行过程中重写请求消息。默认情况下，HTTP / 1.0和HTTP / 1.1通常使用相对请求URI。同样，原始请求可能会多次从一个位置重定向到另一个位置。
	 * 可以使用原始请求和上下文构建最终解释的绝对HTTP位置。该实用程序方法URIUtils#resolve可用于构建用于生成最终请求的已解释绝对URI。此方法包括重定向请求或原始请求中的最后一个片段标识符。
	 * @throws IOException 
	 * @throws ClientProtocolException
	 */
	public void testRedirectHandling() throws ClientProtocolException, IOException {
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		CloseableHttpClient httpclient = HttpClients.custom().setRedirectStrategy(redirectStrategy).build();
		
		CloseableHttpClient httpclient2 = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		HttpGet httpget = new HttpGet("http://localhost:8080/");
		CloseableHttpResponse response = httpclient2.execute(httpget, context);
		try {
		    HttpHost target = context.getTargetHost();
		    List<URI> redirectLocations = context.getRedirectLocations();
		    URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
		    System.out.println("Final HTTP location: " + location.toASCIIString());
		    // Expected to be an absolute URI
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    response.close();
		}
	}
	
	public void testCookie() {
		BasicClientCookie cookie = new BasicClientCookie("name", "value");
		// Set effective domain and path attributes
		cookie.setDomain(".mycompany.com");
		cookie.setPath("/");
		// Set attributes exactly as sent by the server
		cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
		cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".mycompany.com");
		
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		RequestConfig localConfig = RequestConfig.copy(globalConfig).setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		HttpGet httpGet = new HttpGet("/");
		httpGet.setConfig(localConfig);
		
		PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();

		Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
				.register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider(publicSuffixMatcher))
				.register(CookieSpecs.STANDARD, new RFC6265CookieSpecProvider(publicSuffixMatcher))
				.register("easy", new DefaultCookieSpecProvider()).build();

		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("easy").build();

		CloseableHttpClient httpclient2 = HttpClients.custom().setDefaultCookieSpecRegistry(r)
				.setDefaultRequestConfig(requestConfig).build();
		
		// Create a local instance of cookie store
		CookieStore cookieStore = new BasicCookieStore();
		// Populate cookies if needed
		BasicClientCookie cookie3 = new BasicClientCookie("name", "value");
		cookie.setDomain(".mycompany.com");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		// Set the store
		CloseableHttpClient httpclient3 = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		
		
		
	}
}
