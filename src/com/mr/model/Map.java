package com.mr.model;

import java.util.ArrayList;
/**
 * ��ͼ��
 * 
 * @author mingrisoft
 *
 */

public class Map {
    private RigidBody matrix[][];// ��ͼ��������
    private Player player;// ��ͼ�е���Ҷ���
    private ArrayList<Box> boxs;// ��ͼ�а����������б�

    public Map(RigidBody matrix[][]) {
        this.matrix = matrix;
        player = new Player();
        boxs = new ArrayList<>();
        init();
    }
    /**
     * ��ȡ��ͼ��������
     * 
     * @return
     */
    public RigidBody[][] getMapData() {
        return matrix;
    }
    /**
     * ��ͼ���ݳ�ʼ��,�ҳ���ͼ��������Һ����ӵ�λ�ã���������¼��Щλ�ã�ͬʱ��ԭ��������Һ����ӵĶ������
     */
    private void init() {
        // ������ͼ����
        for (int i = 0, ilength = matrix.length; i < ilength; i++) {
            for (int j = 0, jlength = matrix[i].length; j < jlength; j++) {
                RigidBody rb = matrix[i][j];// �����������
                if (rb instanceof Player) {// ��������
                    player.x = i;// ��¼�û�����������
                    player.y = j;// ��¼�û�����������
                    matrix[i][j] = null;// ���������µĸ���������
                } else if (rb instanceof Box) {
                    Box box = new Box(i, j);// ������Ӧ�ú����������������Ӷ���
                    boxs.add(box);// �����б��������
                    matrix[i][j] = null;// ���������µĸ���������
                }
            }
        }
    }
    /**
     * ��ȡ��ͼ�е���Ҷ���
     * 
     * @return
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * ��ȡ��ͼ�е������б�
     * 
     * @return
     */
    public ArrayList<Box> getBoxs() {
        return boxs;
    }

}
