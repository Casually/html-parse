package cc.casually.htmlParse.test;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Response;
import cc.casually.htmlParse.http.Headers;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.nodeutil.Nodes;
import org.json.JSONObject;

/**
 * 测试类
 */
public class text {

    /*public static void main(String[] args) {
      *//*  HttpServletRequest request1 = null;
        ObtainWxInfo obtainWxInfo = new ObtainWxInfo("wxf7111d205bdda02a","c1d8ded0605f8a251d38f2e2680ce362");
        obtainWxInfo.getWxInfo(request1);*//*

        SendDateInfo sendDateInfo = new SendDateInfo();
        sendDateInfo.setUri("http://127.0.0.1/qwt/wx/wxpay/addwxpay.html");
        sendDateInfo.setUserName("王丰");
        sendDateInfo.setUserPhone("13545675856");
        sendDateInfo.addParam("wxPayId","123456");
        sendDateInfo.addParam("openId","oAJxvwD7U9ZnWUTMFfMJikhH4NrM");
        sendDateInfo.addParam("nickName","Casually%F0%9F%98%AF");
        Map<String,String> result = SendDataToServer.sendDataInfo(sendDateInfo);
        System.out.println(result.get("state"));

    }*/

   public static void main(String[] args) {
        Request request = new Request();
        //登陆系统
        request.setUri("http://www.tskspx.com/userLogin.do");
        request.addParam("userid","18710716894");
        request.addParam("password","123456");
        Response response = HttpClient.post(request);
        JSONObject jsonObject = response.getBodyJson();
        /*System.out.println(jsonObject.toString());
        System.out.println(response.getHeaderStr());*/

        //进入系统
        request.addHeader(Headers.COOKIE,response.getHeader().get("Set-Cookie").toString());
        if (jsonObject.getInt("projectNum") != 0 ){
            request.setUri("http://www.tskspx.com/toSelectProject.do");
            request.deleteParamAll();
            response = HttpClient.post(request);
            /*System.out.println(response.getBodyStr());*/
            /*System.out.println(response.getHeaderStr());*/
        }

        //选择题型
        request.setUri("http://www.tskspx.com/selectProject.do");
        /*System.out.println(request.getHeaderStr());*/
        request.deleteParamAll();
        request.addParam("apId","768007");
        response = HttpClient.httpGet(request);
       /* System.out.println(response.getBodyStr());*/
        /*System.out.println(response.getHeaderStr());*/

        //抓取第一题
        request.setUri("http://www.tskspx.com/user/020550/trainBegin.do");
        /*System.out.println(request.getHeaderStr());*/
        request.deleteParamAll();
        request.addParam("sss","2");
        response = HttpClient.httpGet(request);
        /*System.out.println(response.getBodyStr());*/
        /*System.out.println(response.getHeaderStr());*/

        //抓取下面的题
        request.setUri("http://www.tskspx.com/user/020550/trainBegin.do");
        /*System.out.println(request.getHeaderStr());*/
        request.deleteParamAll();
        request.addParam("currentPage","2");
        request.addParam("stlx","6");
        request.addParam("answer","");
        request.addParam("stbh","2030760");
        response = HttpClient.httpGet(request);
        /*System.out.println(response.getBodyStr());*/
        /*System.out.println(response.getHeaderStr());*/
        //抓取下面的题
        request.setUri("http://www.tskspx.com/user/020550/trainBegin.do");
        /*System.out.println(request.getHeaderStr());*/
        request.deleteParamAll();
        request.addParam("currentPage","5");
        request.addParam("stlx","6");
        request.addParam("answer","");
        request.addParam("stbh","2030760");
        response = HttpClient.httpGet(request);
        String bodyStr =  response.getBodyStr();
        /*System.out.println(bodyStr.split("<table border='0' cellspacing='0' cellpadding='0' class='ks_st'>")[1]);
        System.out.println(response.getHeaderStr());*/

        Nodes nodes = new Nodes(bodyStr);

        for (String str:nodes.getListNodeContextForTag("tr")) {
            System.out.println(str);
        }

    }


}
