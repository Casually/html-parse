package cc.casually.htmlParse.test;

import cc.casually.htmlParse.nodeutil.Node;
import cc.casually.htmlParse.nodeutil.Nodes;
import cc.casually.htmlParse.tskspx.Tskspx;

/**
 * 单节点测试
 * @user Administrator
 * @author
 * @CreateTime 2017/10/26 16:52
 */
public class SingleTest {


    public static void main(String[] args) {
        Tskspx tskspx = new Tskspx();
        tskspx.loginSystem("18710716894","123456");
        tskspx.intoSystem();
        tskspx.choiceQuestionType("768007");
        String topicHtmlstr = tskspx.getTopicHtmlStr("020553",String.valueOf(1),"6","2033073");
        Nodes nodes = new Nodes(topicHtmlstr);
        for (Node node:nodes.getListNodeForTag("script")) {
            System.out.println(node.getContext());
            System.out.println(node.getAttributeValue("src"));
            System.out.println(node.getAttributeValue("rel"));
            System.out.println(node.getAttributeValue("type"));
        }
    }
}
