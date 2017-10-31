package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

public class HumanBodySegment implements InterfaceInterchange {

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
    public HumanBodySegment(String image_file) {
        super();
        this.image_file = image_file;
        this.image_base64 = image_file;
        this.image_url = image_file;
    }

    /**
     * 人形抠图
     * @return
     */
    @Override
    public Response getResponse() {
        Request request = new Request();
        request.setUri(StaticData.HUMANBODY_SEGMENT_URL);
        request.addParam("api_key",StaticData.API_KEY);
        request.addParam("api_secret",StaticData.API_SECRET);
        request.addParam("image_url",image_url);
        Response response = HttpClient.post(request);
        return response;
    }
}
