package com.mr.model;

import java.awt.Image;

/**
 * 刚体
 * 
 * @author mingrisoft
 *
 */

public abstract class RigidBody {
    public int x;// 横坐标索引
    public int y;// 纵坐标索引
    private Image image;// 图片

    public RigidBody(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public RigidBody(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    /**
     * 重写equals()方法，用x和y来确定刚体对象的唯一性
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RigidBody other = (RigidBody) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}
