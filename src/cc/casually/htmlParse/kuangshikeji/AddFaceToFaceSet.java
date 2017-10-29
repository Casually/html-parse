package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

/**
 * 加载人脸数据到人脸集合中
 * @author 13545
 * @create-time 2017/10/29 13:19
 */
public class AddFaceToFaceSet implements InterfaceInterchange {

    /**
     * 人脸集合
     */
    private String faceset_token;
    /**
     * 要加入的人脸标识
     */
    private String face_tokens;

    /**
     * 构造方法
     * @param faceset_token 人脸集合
     * @param face_tokens 要加入的人脸标识
     */
    public AddFaceToFaceSet(String faceset_token,String face_tokens) {
        this.faceset_token = faceset_token;
        this.face_tokens = face_tokens;
    }

    @Override
    public Response getResponse() {
        Request request = new Request();
        request.setUri(StaticData.ADD_FACE_URL);
        request.addParam("api_key",StaticData.API_KEY);
        request.addParam("api_secret",StaticData.API_SECRET);
        request.addParam("faceset_token",faceset_token);
        request.addParam("face_tokens",face_tokens);
        //request.addParam("outer_id",StaticData.OUTER_ID);
        Response response = HttpClient.post(request);
        return response;
    }
}
