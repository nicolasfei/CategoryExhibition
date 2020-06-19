package com.nicolas.categoryexhibition.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeUtil {
    public static List<Node> buildTree(List<Node> nodes) {
        Map<Integer, List<Node>> sub = nodes.stream().filter(node -> node.getAttr().getPid() != 0).collect(Collectors.groupingBy(node -> node.getAttr().getPid()));
        nodes.forEach(node -> node.setSub(sub.get(node.getAttr().getId())));
        return nodes.stream().filter(node -> node.getAttr().getPid() == 0).collect(Collectors.toList());
    }
}
