package cc.casually.htmlParse.http;

import cc.casually.htmlParse.staticdata.RegexStaticData;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 请求类
 */
public class HttpClient {

    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * post方式请求服务器(https协议)
     *
     * @param request 请求内容
     * @return AipResponse
     */
    public static Response post(Request request) {
        String url;
        String charset = request.getContentEncoding();
        String content = request.getBodyStr();
        HashMap<String, String> header = request.getHeaders();
        Response response = new Response();
        try {

            boolean isLeng = false;
            if (request.getParamStr().length()>256){
                isLeng = true;
            }

            if (request.getParams().isEmpty()) {
                url = request.getUri().toString();
            }
            else {
                url = isLeng?request.getUri().toString():String.format("%s?%s", request.getUri().toString(), request.getParamStr());
            }

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                    new java.security.SecureRandom());

            URL console = new URL(url);
            Proxy proxy = request.getConfig() == null ? Proxy.NO_PROXY : request.getConfig().getProxy();
            HttpURLConnection conn = (HttpURLConnection) console.openConnection(proxy);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");// 设置URL请求方法

            if (url.contains("https")) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
                httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            }

            // set timeout
            if (request.getConfig() != null) {
                conn.setConnectTimeout(request.getConfig().getConnectionTimeoutMillis());
                conn.setReadTimeout(request.getConfig().getSocketTimeoutMillis());
            }
            // 添加header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(content.getBytes(charset));

            if (isLeng){
                out.write(request.getParamStr().getBytes(charset));
            }

            out.flush();
            out.close();
            int statusCode = conn.getResponseCode();
            response.setHeader(conn.getHeaderFields());
            response.setStatus(statusCode);
            response.setCharset(charset);
            InputStream is;
            if (statusCode != 200) {
                is = conn.getErrorStream();
            }else{
                is = conn.getInputStream();
            }

            if (is != null) {
                response.setBody(is);
            }
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * get请求方法
     * @param request
     * @return
     */
    public static Response httpGet(Request request){
        String url;
        String result = "";
        BufferedReader in = null;
        Response response = new Response();
        if (request.getParams().isEmpty()) {
            url = request.getUri().toString();
        }else {
            url = String.format("%s?%s", request.getUri().toString(), request.getParamStr());
        }
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            HashMap<String,String> headers = request.getHeaders();
            for (String key:headers.keySet()){
                connection.setRequestProperty(key,headers.get(key));
            }
            //connection.setRequestProperty("accept", "*/*");
            /*connection.setRequestProperty("connection", "Keep-Alive");
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
            connection.setRequestProperty(Headers.USER_AGENT,!(request.getHeaders().get(Headers.USER_AGENT) == null)?
                    request.getHeaders().get(Headers.USER_AGENT):userAgent);
            connection.setRequestProperty(Headers.COOKIE,request.getHeaders().get(Headers.COOKIE));*/
            connection.connect();
            response.setHeader(connection.getHeaderFields());
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            response.setBody(result.getBytes(request.getContentEncoding()));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return response;
    }

    /**
     * post请求
     * @param request
     * @return
     */
    public static Response httpPost(Request request){
        Response resultResponse = new Response();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(request.getUri());

        for (Map.Entry<String,String> entry:request.getHeaders().entrySet()) {
            httpPost.setHeader(entry.getKey(),entry.getValue());
        }

        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            resultResponse.setBody(is);
            resultResponse.setStatus(statusCode);
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultResponse;
    }

    public static Response postFile(Request request) {
        Response resultResponse = new Response();
        HttpPost httpPost = new HttpPost(request.getUri());

        Iterator var3 = request.getHeaders().entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var3.next();
            httpPost.setHeader((String)entry.getKey(), (String)entry.getValue());
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        Iterator var4 = request.getParams().entrySet().iterator();

        while(var4.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var4.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            File file = new File(value);
            if (file.isFile()) {
                builder.addBinaryBody(key, file);
            } else {
                builder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset("UTF-8"));
            }
        }

        HttpEntity httpEntity = builder.build();
        httpPost.setEntity(httpEntity);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            resultResponse.setBody(is);
            resultResponse.setStatus(statusCode);
            EntityUtils.consume(entity);
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return resultResponse;
    }

    public static Response doDelete(Request request){
        Response response = new Response();
        String result = "";
        BufferedReader in = null;
        String url;
        if (request.getParams().isEmpty()) {
            url = request.getUri().toString();
        } else {
            url = String.format("%s?%s", request.getUri().toString(), request.getParamStr());
        }
        try {
            URL realUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)realUrl.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("DELETE");
            HashMap<String,String> headers = request.getHeaders();
            for (String key:headers.keySet()){
                httpURLConnection.setRequestProperty(key,headers.get(key));
            }
            httpURLConnection.connect();
            httpURLConnection.disconnect();
            response.setHeader(httpURLConnection.getHeaderFields());

            String line;
            for(in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())); (line = in.readLine()) != null; result = result + line) {
                ;
            }

            response.setBody(result.getBytes(request.getContentEncoding()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
