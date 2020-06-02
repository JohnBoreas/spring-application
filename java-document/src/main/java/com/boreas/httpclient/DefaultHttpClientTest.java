package com.boreas.httpclient;

import org.apache.http.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public class DefaultHttpClientTest {

    public void doFetch() {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpUriRequest method = methodType.gen(url, parameters);
        method.setHeader("Accept-Encoding", "gzip, deflate");
        if (config != null) {
            config.render(client, method);
        }
        HttpResponse response = null;
        SimpleHttpResponse shr = null;
        String finalUrl = null;
        try {
            HttpContext context = new BasicHttpContext();
            response = client.execute(method, context);
            int responseStatus = response.getStatusLine().getStatusCode();
            if (responseStatus != HttpStatus.SC_OK && responseStatus != 404) {
                throw new IOException(String.format("Http error: url=%s, status line: %s", url, response.getStatusLine().toString()));
            } else {
                HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
                        ExecutionContext.HTTP_REQUEST);
                HttpHost currentHost = (HttpHost) context.getAttribute(
                        ExecutionContext.HTTP_TARGET_HOST);
                finalUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());
            }
            shr = new SimpleHttpResponse();
            shr.setResponseCode(responseStatus);
            shr.setFinalUrl(finalUrl);
            shr.setResponseHeader(headerToMap(response.getAllHeaders()));
            HttpGzip gzip = checkAndGetGzip(response);
            String body = doGzipNew(response, config.getDefCharset(), gzip);
            shr.setBody(body);
        } catch (HttpException e) {
            throw new HttpException(String.format("Http error: url=%s", url), e);
        } catch (IOException e) {
            throw new HttpException(String.format("IO error: url=%s", url), e);
        } catch (Exception e) {
            throw new HttpException(String.format("unknow error: url=%s", url), e);
        } finally {
            try {
                if (response != null) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        EntityUtils.consume(entity);
                    }
                }
            } catch (IOException e1) {
            }
            try {
                client.getConnectionManager().shutdown();
            } catch (Exception e) {
            }
        }
        return shr;
    }

    private static String doGzipNew(HttpResponse response, String defCharset, HttpGzip gzip) throws IOException {
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        if (is != null) {
            try {
                String charset = null;
                org.apache.http.Header respHeader = entity.getContentType();
                if (respHeader != null) {
                    charset = getCharset(respHeader.getValue());
                }
                charset = (charset == null ? (defCharset == null ? Constants.CHARSET_ISO: defCharset) : charset);
                return IoExtUtils.getStreamAsString(is, charset, gzip);
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
        return null;
    }
}
