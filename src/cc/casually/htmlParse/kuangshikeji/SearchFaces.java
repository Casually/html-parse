package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

/**
 * 在人脸集合中查找相识度高的脸
 * @author 13545
 * @create-time 2017/10/29 14:14
 */
public class SearchFaces implements InterfaceInterchange{

    /**
     * base64格式图片
     */
    private String image_base64;
    /**
     * 图片地址
     */
    private String image_url;
    /**
     * 二进制图片
     */
    private String image_file;
    /**
     * 人脸标识
     */
    private String face_token;
    /**
     * 人脸集合
     */
    private String faceset_token;

    /**
     * 构造方法
     * @param image_base64 图片
     * @param faceset_token 人脸集合
     */
    public SearchFaces(String image_base64,String faceset_token) {
        this.face_token = image_base64;
        this.image_file = image_base64;
        this.image_base64 = image_base64;
        this.image_url = image_base64;
        this.faceset_token = faceset_token;
    }

    /**
     * 查询人脸
     * @return
     */
    @Override
    public Response getResponse() {
        Request request = new Request();
        request.setUri(StaticData.SEARCH_URL);
        request.addParam("api_key",StaticData.API_KEY);
        request.addParam("api_secret",StaticData.API_SECRET);
        request.addParam("image_url",image_url);
        request.addParam("faceset_token",faceset_token);
        Response response = HttpClient.post(request);
        return response;
    }
}
