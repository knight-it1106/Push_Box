package com.mr.model;

import java.awt.Image;

/**
 * ����
 * 
 * @author mingrisoft
 *
 */

public abstract class RigidBody {
    public int x;// ����������
    public int y;// ����������
    private Image image;// ͼƬ

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
     * ��дequals()��������x��y��ȷ����������Ψһ��
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
