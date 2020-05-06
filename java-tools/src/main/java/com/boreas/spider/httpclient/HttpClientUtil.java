package com.boreas.spider.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
/**
 * 
 * @author xuhua.jiang
 * @date: 2020年3月27日
 */
public class HttpClientUtil {

	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	/**
	 * 上传文件文件
	 */
	public static String uploadFileBySearchApi(String url, byte[] stream, String name, String fileName) {
		if (stream == null) {
			logger.info("stream is empty:" + url);
			return null;
		}
	    HttpClient httpClient = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(url);
	    //添加认证消息头
	    try {
	        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
	        //添加要上传的文件
	        multipartEntityBuilder.addBinaryBody(name, stream, ContentType.WILDCARD, fileName).setMode(HttpMultipartMode.RFC6532);
	        httpPost.setEntity(multipartEntityBuilder.build());
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        int code = httpResponse.getStatusLine().getStatusCode();
	        if (code == 200) {
	            String strResult = EntityUtils.toString(httpResponse.getEntity());
	            logger.info(strResult);
	            return strResult;
	        } else{
	            HttpEntity httpEntity = httpResponse.getEntity();
	            String content = EntityUtils.toString(httpEntity);
	            logger.info(content);
	            return content;
	        }
	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    return null;
	}
}
