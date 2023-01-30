#### 一、概念

远程过程调用



#### 二、提供方法

```js
delete() 在特定的URL上对资源执行HTTP DELETE操作

exchange()
在URL上执行特定的HTTP方法，返回包含对象的ResponseEntity，这个对象是从响应体中
映射得到的

execute() 在URL上执行特定的HTTP方法，返回一个从响应体映射得到的对象

getForEntity() 发送一个HTTP GET请求，返回的ResponseEntity包含了响应体所映射成的对象

getForObject() 发送一个HTTP GET请求，返回的请求体将映射为一个对象

postForEntity()
POST 数据到一个URL，返回包含一个对象的ResponseEntity，这个对象是从响应体中映射得
到的

postForObject() POST 数据到一个URL，返回根据响应体匹配形成的对象

headForHeaders() 发送HTTP HEAD请求，返回包含特定资源URL的HTTP头

optionsForAllow() 发送HTTP OPTIONS请求，返回对特定URL的Allow头信息

postForLocation() POST 数据到一个URL，返回新创建资源的URL

put() PUT 资源到特定的URL
```



#### 三、底层实现

采用了Apache的http包下的CloseableHttpClient实现

```java
@Nullable
	protected <T> T doExecute(URI url, @Nullable HttpMethod method, @Nullable RequestCallback requestCallback,
			@Nullable ResponseExtractor<T> responseExtractor) throws RestClientException {

		Assert.notNull(url, "URI is required");
		Assert.notNull(method, "HttpMethod is required");
		ClientHttpResponse response = null;
		try {
			ClientHttpRequest request = createRequest(url, method);
			if (requestCallback != null) {
				requestCallback.doWithRequest(request);
			}
			response = request.execute();
			handleResponse(url, method, response);
			return (responseExtractor != null ? responseExtractor.extractData(response) : null);
		}
		catch (IOException ex) {
			String resource = url.toString();
			String query = url.getRawQuery();
			resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
			throw new ResourceAccessException("I/O error on " + method.name() +
					" request for \"" + resource + "\": " + ex.getMessage(), ex);
		}
		finally {
			if (response != null) {
				response.close();
			}
		}
	}
```

