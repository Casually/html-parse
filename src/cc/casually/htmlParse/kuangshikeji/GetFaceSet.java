package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

public class GetFaceSet implements InterfaceInterchange {
    @Override
    public Response getResponse() {
        Request request = new Request();
        request.setUri(StaticData.GET_FACESET_URL);
        request.addParam("api_key",StaticData.API_KEY);
        request.addParam("api_secret",StaticData.API_SECRET);
        request.addParam("outer_id",StaticData.OUTER_ID);
        Response response = HttpClient.post(request);
        return response;
    }
}
