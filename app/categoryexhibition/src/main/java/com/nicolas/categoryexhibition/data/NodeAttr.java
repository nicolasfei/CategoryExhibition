package com.nicolas.categoryexhibition.data;

import java.io.Serializable;

public class NodeAttr implements Serializable {
    private static final long serialVersionUID = 2L;
    private int id;
    private int pid;
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
