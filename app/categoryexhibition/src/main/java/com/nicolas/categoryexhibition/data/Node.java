package com.nicolas.categoryexhibition.data;

import com.nicolas.categoryexhibition.component.ChoiceStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * pid 为 0 的是根节点
 */
public class Node {
    private NodeAttr attr;
    private List<Node> sub = new ArrayList<>();
    /**
     * 节点的选中状态----未选中（包括子节点都没有被选中） ----部分子节点被选中  ----子节点全部被选中
     */
    private ChoiceStatus status = ChoiceStatus.None;

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

    public void setStatus(ChoiceStatus status) {
        this.status = status;
        switch (this.status) {
            case Part:
                break;
            case None:
                if (this.sub.size() > 0) {
                    for (Node node : this.sub) {
                        node.setStatus(ChoiceStatus.None);
                    }
                }
                break;
            case All:
                if (this.sub.size() > 0) {
                    for (Node node : this.sub) {
                        node.setStatus(ChoiceStatus.All);
                    }
                }
                break;
        }
    }

    public ChoiceStatus getStatus() {
        return status;
    }
}
