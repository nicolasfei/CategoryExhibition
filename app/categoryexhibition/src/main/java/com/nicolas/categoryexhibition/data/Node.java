package com.nicolas.categoryexhibition.data;

import java.util.ArrayList;
import java.util.List;

/**
 * pid 为 0 的是根节点
 */
public class Node {
    private NodeAttr attr;
    private List<Node> sub = new ArrayList<>();

    public Node(NodeAttr attr) {
        this.attr = attr;
    }

    public NodeAttr getAttr() {
        return attr;
    }

    public List<Node> getSub() {
        return sub;
    }

    public void setSub(List<Node> sub) {
        this.sub = sub;
    }
}
