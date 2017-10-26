package cc.casually.htmlParse.test;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;
import cc.casually.htmlParse.nodeutil.Node;
import cc.casually.htmlParse.nodeutil.Nodes;

import java.util.List;

public class baidu {
    public static void main(String[] args) {
        Request request = new Request();
        request.setUri("http://www.u17.com/chapter/225308.html#image_id=1624633");
        Response response = HttpClient.post(request);
        String bodyStr = response.getBodyStr();
        Nodes nodes = new Nodes(bodyStr);
        List<Node> listNode = nodes.getListNodeForTag("img");
        for (Node node:listNode) {
            System.out.println(node.getContext());
            if(node.getAttributeValue("src")!=null){
                System.out.println(node.getAttributeValue("src"));
            }
        }
    }
}
