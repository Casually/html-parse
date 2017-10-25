package cc.makalu.wxutil.http;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (request.getParams().isEmpty()) {
                url = request.getUri().toString();
            }
            else {
                url = String.format("%s?%s", request.getUri().toString(), request.getParamStr());
            }

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                    new java.security.SecureRandom());

            URL console = new URL(url);
            Proxy proxy = request.getConfig() == null ? Proxy.NO_PROXY : request.getConfig().getProxy();
            HttpURLConnection conn = (HttpURLConnection) console.openConnection(proxy);
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
            conn.setDoOutput(true);
            // 添加header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(content.getBytes(charset));
            out.flush();
            out.close();
            int statusCode = conn.getResponseCode();
            response.setHeader(conn.getHeaderFields());
            response.setStatus(statusCode);
            response.setCharset(charset);
            if (statusCode != 200) {
                return response;
            }

            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                response.setBody(outStream.toByteArray());
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
        System.out.println("request = [" + url + "]");
        URL realUrl;
        try {
            realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty(Headers.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
            connection.setRequestProperty(Headers.COOKIE,request.getHeaders().get(Headers.COOKIE));
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
}
