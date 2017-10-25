package cc.makalu.wxutil.nodeutil;

import javafx.beans.binding.StringBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 节点工具类
 * @user Administrator
 * @author
 * @CreateTime 2017/10/24 14:46
 */
public class NodeUtil123 {

    private static String nodePattern = "<[/\\w]+?>";

    private static Pattern patternNodes = Pattern.compile(nodePattern);

    /**
     * 获取所有的节点标签信息
     * 节点标签名称 tag
     * 节点开始字符串位置 start
     * 节点当前编号 num
     * 节点整体编号 entiretyNum
     * @param str
     * @return
     */
    public Map<String,List<HashMap<String,Object>>> getNodeS(String str){
        List<HashMap<String,Object>> nodeStartS = new ArrayList<>();
        List<HashMap<String,Object>> nodeEndS = new ArrayList<>();
        Matcher matcher = patternNodes.matcher(str);
        int startNum = 0;
        int endNum = 0;
        int entiretyNum = 0;
        while (matcher.find()){
            HashMap<String,Object> nodeM = new HashMap<>();
            String nodeTag = matcher.group();
            int start = matcher.start();
            nodeM.put("tag",nodeTag);
            nodeM.put("start",start);
            nodeM.put("entiretyNum",entiretyNum);
            if(nodeTag.indexOf("/") != -1){
                nodeM.put("num",endNum);
                endNum++;
                nodeEndS.add(nodeM);
            }else{
                nodeM.put("num",startNum);
                startNum++;
                nodeStartS.add(nodeM);
            }
            entiretyNum++;
        }
        Map<String,List<HashMap<String,Object>>> result = new HashMap<>();
        result.put("nodeStartS",nodeStartS);
        result.put("nodeEndS",nodeEndS);
        return result;
    }

    /**
     * 将Map转换成String
     * @param hashMap
     * @return
     */
    public String getHashMapStr(HashMap<String,Object> hashMap){
        StringBuilder hashMapStr = new StringBuilder();
        hashMapStr.append("[ ");
        for (String key:hashMap.keySet()) {
            hashMapStr.append(String.format("%s=%s, ",key,hashMap.get(key)));
        }
        hashMapStr.deleteCharAt(hashMapStr.length() - 1);
        hashMapStr.append(" ]");
        return hashMapStr.toString();
    }

}
