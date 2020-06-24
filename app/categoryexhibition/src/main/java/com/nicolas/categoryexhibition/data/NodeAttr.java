package com.nicolas.categoryexhibition.data;

import java.io.Serializable;

public class NodeAttr implements Serializable {
    private static final long serialVersionUID = 2L;
    private int id;             //节点ID
    private int pid;            //节点父ID
    private String name;
    private String imgURL;

    public NodeAttr(int id, int pid, String name, String imgURL) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.imgURL = imgURL;
    }

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getImgURL() {
        return imgURL;
    }
}
