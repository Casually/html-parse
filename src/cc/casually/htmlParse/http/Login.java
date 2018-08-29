package cc.casually.htmlParse.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * 登录系统
 *
 * @author 13545
 */
public class Login {

    private Request request;

    public Login() {
        this.request = new Request();
    }

    /**
     * 登录系统
     *
     * @param userName 登录用户名
     * @param password 登录密码
     * @return
     */
    public Map<String, Object> loginSystem(String URI, String userName, String password) {
        Map<String, Object> reslut = new HashMap<>();
        request.setUri(URI);
        request.addParam("userid", userName);
        request.addParam("password", password);
        Response response = HttpClient.post(request);
        int status = response.getStatus();
        reslut.put("status", status);
        reslut.put("response", response);
        if (status != 200) {
            reslut.put("state", "error");
        } else {
            reslut.put("state", "success");
        }
        request.addHeader(Headers.COOKIE, response.getHeader().get("Set-Cookie").toString());
        reslut.put("cookie", response.getHeader().get("Set-Cookie").toString());
        return reslut;
    }

    /**
     * 登录 获取 Cookie
     *
     * @param url      API地址
     * @param username 账号, 注意不要使用admin@megvii.com
     * @param password 密码
     * @return cookie CookieStore
     * @throws Exception
     */
    public CookieStore authLogin(String url, String username, String password) throws Exception {
        System.out.println("Start /auth/login to ...");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        //设置user-agent为 "Koala Admin"
        //设置Content-Type为 "application/json"
        /*request.setHeader("User-Agent", "Koala Admin");*/
        request.setHeader("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);

        request.setEntity(new StringEntity(json.toString(), "UTF-8"));

        //发起网络请求，获取结果值
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = httpclient.execute(request, context);
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

        //解析JSON数据
        JSONObject resp = new JSONObject(responseBody);
        int result = resp.optInt("code", -1);
        if (result != 0) {
            System.err.println("Login failed, code:" + result);
        } else {
            System.out.println("Login Success,id:" + resp.getJSONObject("data").getInt("id"));
            return context.getCookieStore();
        }
        return null;
    }

    /**
     * 进入系统
     *
     * @return
     */
    public Response intoSystem(String URI, String cookie) {
        this.request.addHeader(Headers.COOKIE, cookie);
        this.request.setUri(URI);
        this.request.deleteParamAll();
        this.request.setContentEncoding("GBK");
        return HttpClient.post(request);
    }

}
