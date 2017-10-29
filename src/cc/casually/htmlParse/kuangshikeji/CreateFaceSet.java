package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

/**
 *  创建人脸的集合 FaceSet
 * @author 13545
 * @create-time 2017/10/29 13:17
 */
public class CreateFaceSet implements InterfaceInterchange {

    /**
     * 创建人脸集合
     * @return
     */
    @Override
    public Response getResponse() {
        Request request = new Request();
        request.setUri(StaticData.CREATE_FACESET_URL);
        request.addParam("api_key",StaticData.API_KEY);
        request.addParam("api_secret",StaticData.API_SECRET);
        request.addParam("display_name","test");
        request.addParam("outer_id",StaticData.OUTER_ID);
        Response response = HttpClient.post(request);
        return response;
    }
}
