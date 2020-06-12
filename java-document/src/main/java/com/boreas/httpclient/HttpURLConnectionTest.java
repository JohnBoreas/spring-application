package com.boreas.httpclient;

import com.gargoylesoftware.htmlunit.util.UrlUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionTest {

    public void openClient() {
        try {
            URL url = new URL("http://localhost:8080/TestHttpURLConnectionPro/index.jsp");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpURLConnection connection(String _url, String method, String ctype, Map<String,String> headerMap, Proxy proxy) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] {new X509ExtendedTrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                    }
                }}, new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = null;
            if(proxy == null) {
                connHttps = (HttpsURLConnection) url.openConnection();
            } else {
                connHttps = (HttpsURLConnection) url.openConnection(proxy);
            }
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            if(proxy!=null){
                conn = (HttpURLConnection) url.openConnection(proxy);
            }else
                conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        Map<String,String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
        requestHeaders.put("Accept-Encoding", "gzip, deflate");
        requestHeaders.put("Content-Type", ctype);
        // when outer referer is null or empty, this Referer dont be itself
//		requestHeaders.put("Referer", _url);
        if(headerMap!=null)
            requestHeaders.putAll(headerMap);
        for(Map.Entry<String, String> entry:requestHeaders.entrySet()){
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return conn;
    }
}
