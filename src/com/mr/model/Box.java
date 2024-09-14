package com.mr.model;

import com.mr.util.GameImageUtil;
/**
 * ����
 * @author mingrisoft
 *
 */

public class Box extends RigidBody {
    private boolean arrived = false;// �Ƿ񵽴�Ŀ�ĵ�

    public Box() {
        super(GameImageUtil.boxImage1);
    }

    public Box(int x, int y) {
        super(x,y);
        setImage(GameImageUtil.boxImage1);
    }

    public void arrive() {// ����
        setImage(GameImageUtil.boxImage2);
        arrived = true;
    }

    public void leave() {// �뿪
        setImage(GameImageUtil.boxImage1);
        arrived = false;
    }

    public boolean isArrived() {
        return arrived;
    }

}
