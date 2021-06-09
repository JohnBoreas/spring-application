package com.boreas.spider.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Description: HttpClient
 * @author xuhua.jiang
 * @date: 2018年7月16日
 */
public class HttpClientUtil {

	public HttpClientUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 抓取网页信息使用get请求
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// 创建httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建httpGet实例
		HttpGet httpGet = new HttpGet("https://list.tmall.com/search_product.htm?spm=a3204.7084713.1996500281.55.waO88a&user_id=725677994&cat=51440019&active=1&acm=lb-zebra-26901-351264.1003.4.468294&style=g&sort=s&scm=1003.4.lb-zebra-26901-351264.OTHER_14440874222721_468294&industryCatId=51462017&s=0");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		if (response != null) {
			HttpEntity entity = response.getEntity(); // 获取网页内容
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
}
