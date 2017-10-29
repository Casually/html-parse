package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

public class test {

    /**
     * 第一步：创建人脸集合
     * 第二步：进行图片人脸识别
     * 第三步：添加人脸图到人脸集合中、（同步进行--将人脸信息写入到数据库）
     * 第四步：搜索人脸
     * 第五步：对比数据库数据
     * @param args
     */
    public static void mai1n(String[] args) {
        //Response response = new GetFaceSet().getResponse();
        //Response response =  new Detect("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509295274450&di=a89b8f20d1c306d45b75a7ab22325f99&imgtype=0&src=http%3A%2F%2Fmvimg1.meitudata.com%2F56966d570eaba4885.jpg").getResponse();
        //Response response = new AddFaceToFaceSet("06ee32255b44ba8ebf6541a80da1313e","140323a6b8fe05c686c3f6a78d2436c4").getResponse();
        Response response = new SearchFaces("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509295274450&di=a89b8f20d1c306d45b75a7ab22325f99&imgtype=0&src=http%3A%2F%2Fmvimg1.meitudata.com%2F56966d570eaba4885.jpg","06ee32255b44ba8ebf6541a80da1313e").getResponse();
        System.out.println(response.getStatus());
        System.out.println(response.getBodyStr());
    }
}
