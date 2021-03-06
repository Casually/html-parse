package cc.casually.htmlParse.nodeutil;

import cc.casually.htmlParse.http.HttpClient;
import cc.casually.htmlParse.http.Request;
import cc.casually.htmlParse.http.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 节点
 *
 * @author
 * @user Administrator
 * @CreateTime 2017/10/24 19:28
 */
public class Nodes {

    private String htmlStrOrPathOrURi = "";
    private List<Node> listNode;
    private Map<String, List<Node>> mapListNode;
    private String filePathRegex = "[a-zA-Z]:\\\\.*";
    private String htmlUriRegex = "(http://|https://).*";

    /**
     * @param htmlStrOrPathOrURi
     */
    public Nodes(String htmlStrOrPathOrURi) {
        this.listNode = new ArrayList<Node>();
        this.htmlStrOrPathOrURi = htmlStrOrPathOrURi;
        NodeUtil nodeUtil = new NodeUtil();
        if (htmlStrOrPathOrURi.matches(htmlUriRegex)) {
            Request request = new Request();
            request.setUri(htmlStrOrPathOrURi);
            Response response = HttpClient.post(request);
            this.htmlStrOrPathOrURi = response.getBodyStr();
        }
        if (new File(filePathRegex).isFile()) {
            StringBuilder stringBuilder = new StringBuilder();
            String tmpPath = htmlStrOrPathOrURi;
            File file = new File(tmpPath);
            BufferedReader bufread;
            String read;
            try {
                bufread = new BufferedReader(new FileReader(file));
                while ((read = bufread.readLine()) != null) {
                    stringBuilder.append(read);
                }
                bufread.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.htmlStrOrPathOrURi = stringBuilder.toString();
        }
        mapListNode = nodeUtil.getNodeS(this.htmlStrOrPathOrURi);
        this.listNode = getListNodeAllPr();
    }

    /**
     * 获取所有的节点
     *
     * @return
     */
    public List<Node> getListNodeAll() {
        return listNode;
    }

    /**
     * 获取所有节点信息
     *
     * @return
     */
    private List<Node> getListNodeAllPr() {

        for (Node nodeStartS : mapListNode.get("nodeStartS")) {

            String hmsTag = nodeStartS.getTag();
            Integer hmsStart = nodeStartS.getStart();

            for (Node nodeEndS : mapListNode.get("nodeEndS")) {
                Node node = new Node();
                boolean flag = false;
                String hmeTag = nodeEndS.getTag();
                Integer hmeStart = nodeEndS.getStart();

                //判断当前尾标签开始点是否大于当前头标签开始点
                if (hmeStart < hmsStart) {
                    continue;
                }

                //判断当前尾标签是否是当前头标签对应的尾标签
                if (hmeTag.indexOf(hmsTag.split("<")[1]) == -1) {
                    continue;
                }

                /**
                 * 获取头标签和第一个对应尾标签之间的标签差
                 */
                int startTagNum = 0;
                for (Node hmss : mapListNode.get("nodeStartS")) {
                    Integer hmssStart = hmss.getStart();
                    String hmssTag = hmss.getTag();

                    if (hmssStart <= hmsStart) {
                        continue;
                    }

                    if (!hmsTag.equals(hmssTag)) {
                        continue;
                    }

                    if (hmssStart <= hmeStart) {
                        startTagNum++;
                    } else {
                        break;
                    }

                }

                /**
                 * 增加递归判断第一个尾标签与最终尾标签之间是否包含对应的头标签
                 * 差值加上包含的个数
                 * 如果包含：就加上包含的个数之后继续判断，直至不包含为止
                 * 得到最终的差值
                 */

                int tmpDifference = 0;
                int fistEndTagStart = nodeEndS.getStart();
                int eneEndTagStart = 0;
                do {
                    tmpDifference = 0;

                    if(node.getTag() != null){
                        fistEndTagStart = node.getStart();
                    }
                    for (Node hmee : mapListNode.get("nodeEndS")) {
                        String hmeeTag = hmee.getTag();
                        Integer hmeeStart = hmee.getStart();
                        if (hmeTag.equals(hmeeTag) && hmeeStart >= hmeStart) {
                            if (Integer.valueOf(tmpDifference).equals(Integer.valueOf(startTagNum))) {
                                node.setTag(hmsTag);
                                node.setContext(htmlStrOrPathOrURi.substring(hmsStart, hmeeStart + hmee.getTag().length()));
                                node.setStart(hmee.getStart());

                                eneEndTagStart = hmee.getStart();
                                flag = true;
                                break;
                            }
                            tmpDifference++;
                        }
                    }

                    tmpDifference = 0;
                    for (Node nodestart : mapListNode.get("nodeStartS")){
                        if(!hmsTag.equals(nodestart.getTag())){
                            continue;
                        }
                        if(nodestart.getStart() < fistEndTagStart){
                            continue;
                        }
                        if(nodestart.getStart() > eneEndTagStart){
                            break;
                        }
                        tmpDifference++;
                    }
                    startTagNum += tmpDifference;
                } while (tmpDifference != 0);

                if(node.getTag() != null){
                    listNode.add(node);
                }
                /**
                 * 获取头标签和对应的尾标签
                 */
                /*int resultNum = 0;
                for (Node hmee : mapListNode.get("nodeEndS")) {
                    String hmeeTag = hmee.getTag();
                    Integer hmeeStart = hmee.getStart();
                    if (hmeTag.equals(hmeeTag) && hmeeStart >= hmeStart) {
                        if (Integer.valueOf(resultNum).equals(Integer.valueOf(startTagNum))) {
                            node.setTag(hmsTag);
                            node.setContext(htmlStrOrPathOrURi.substring(hmsStart, hmeeStart + hmee.getTag().length()));
                            listNode.add(node);
                            flag = true;
                            break;
                        }
                        resultNum++;
                    }
                }*/

                if (flag) {
                    break;
                }

            }
        }
        //加载单标签
        this.listNode.addAll(mapListNode.get("singleTagS"));
        return listNode;
    }

    /**
     * 获取制定节点信息
     *
     * @param tag
     * @return
     */
    public List<Node> getListNodeForTag(String tag) {
        List<Node> listNodeForTag = new ArrayList<>();
        tag = "<" + tag;
        for (Node node : listNode) {
            if (node.getTag().startsWith(tag)) {
                listNodeForTag.add(node);
            }
        }
        return listNodeForTag;
    }

    /**
     * 获取节点大小
     *
     * @param size
     * @return
     */
    public int getNodeSize(int size) {
        return listNode.size();
    }

    /**
     * 获取指定位置的单节点
     *
     * @param site
     * @return
     */
    public Node getNodeForSite(int site) {
        return listNode.get(site);
    }

    /**
     * 获取指定标签指定位置的节点信息
     *
     * @param tag
     * @param site
     * @return
     */
    public Node getListNodeForTagAndSite(String tag, Integer site) {
        Integer size = 0;
        tag = "<" + tag;
        for (Node node : listNode) {
            if (node.getTag().startsWith(tag)) {
                size++;
                if (site.equals(size)) {
                    return node;
                }
            }
        }
        return null;
    }

    ;


    /**
     * 获取制定标签的上下文信息
     *
     * @param tag
     * @return
     */
    public List<String> getListNodeContextForTag(String tag) {
        List<String> listNodeContextForTag = new ArrayList<>();
        tag = "<" + tag;
        for (Node node : listNode) {
            if (node.getTag().startsWith(tag)) {
                listNodeContextForTag.add(node.getContext());
            }
        }
        return listNodeContextForTag;
    }

    /**
     * 获取所有节点上下文信息
     *
     * @return
     */
    public List<String> getListNodeContextAll() {
        List<String> listNodeContextAll = new ArrayList<>();
        for (Node node : listNode) {
            listNodeContextAll.add(node.getContext());
        }
        return listNodeContextAll;
    }

}
