package cc.makalu.wxutil.nodeutil;

import java.util.HashMap;

public class Node {

    private String tag;
    private String context;
    private int start;
    private int num;
    private int entiretyNum;
    private Node node;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getEntiretyNum() {
        return entiretyNum;
    }

    public void setEntiretyNum(int entiretyNum) {
        this.entiretyNum = entiretyNum;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "Node{" +
                "tag='" + tag + '\'' +
                ", context='" + context + '\'' +
                ", start=" + start +
                ", num=" + num +
                ", entiretyNum=" + entiretyNum +
                '}';
    }
}
