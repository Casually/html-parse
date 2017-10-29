package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

/**
 * 人脸检测
 * @author 13545
 * @create-time 2017/10/29 13:49
 */
public class Detect implements InterfaceInterchange{

    /**
     * 二进制图片文件
     */
    private String image_file;
    /**
     * base64格式文件
     */
    private String image_base64;
    /**
     * 图片下载地址
     */
    private String image_url;

    /**
     * 构造方法
     * @param image_file
     */
    public Detect(String image_file) {
        super();
        this.image_file = image_file;
        this.image_base64 = image_file;
        this.image_url = image_file;
    }

    /**
     * 进行人脸检测
     * @return
     */
    @Override
    public Response getResponse() {
        Request request = new Request();
        request.setUri(StaticData.DETECT_URL);
        request.addParam("api_key",StaticData.API_KEY);
        request.addParam("api_secret",StaticData.API_SECRET);
        request.addParam("image_url",image_base64);
        request.addParam("return_landmark","2");
        Response response = HttpClient.post(request);
        return response;
    }
}
