package cc.casually.htmlParse.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录系统
 * @author 13545
 */
public class Login {

    private Request request;

    public Login() {
        this.request = new Request();
    }

    /**
     * 登录系统
     * @param userName 登录用户名
     * @param password 登录密码
     * @return
     */
    public Map<String,Object> loginSystem(String URI,String userName, String password){
        Map<String,Object> reslut = new HashMap<>();
        request.setUri(URI);
        request.addParam("userid",userName);
        request.addParam("password",password);
        Response response = HttpClient.post(request);
        int status = response.getStatus();
        reslut.put("status",status);
        reslut.put("response",response);
        if(status != 200){
            reslut.put("state","error");
        }else{
            reslut.put("state","success");
        }
        request.addHeader(Headers.COOKIE,response.getHeader().get("Set-Cookie").toString());
        reslut.put("cookie",response.getHeader().get("Set-Cookie").toString());
        return reslut;
    }

    /**
     * 进入系统
     * @return
     */
    public Response intoSystem(String URI,String cookie){
        this.request.addHeader(Headers.COOKIE,cookie);
        this.request.setUri(URI);
        this.request.deleteParamAll();
        this.request.setContentEncoding("GBK");
        return HttpClient.post(request);
    }

}
