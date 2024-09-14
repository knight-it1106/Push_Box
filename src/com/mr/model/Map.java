package com.mr.model;

import java.util.ArrayList;
/**
 * 地图类
 * 
 * @author mingrisoft
 *
 */

public class Map {
    private RigidBody matrix[][];// 地图数据数组
    private Player player;// 地图中的玩家对象
    private ArrayList<Box> boxs;// 地图中包含的箱子列表

    public Map(RigidBody matrix[][]) {
        this.matrix = matrix;
        player = new Player();
        boxs = new ArrayList<>();
        init();
    }
    /**
     * 获取地图数据数组
     * 
     * @return
     */
    public RigidBody[][] getMapData() {
        return matrix;
    }
    /**
     * 地图数据初始化,找出地图数组中玩家和箱子的位置，并单独记录这些位置，同时把原数组中玩家和箱子的对象清空
     */
    private void init() {
        // 遍历地图数组
        for (int i = 0, ilength = matrix.length; i < ilength; i++) {
            for (int j = 0, jlength = matrix[i].length; j < jlength; j++) {
                RigidBody rb = matrix[i][j];// 读出刚体对象
                if (rb instanceof Player) {// 如果是玩家
                    player.x = i;// 记录用户横坐标索引
                    player.y = j;// 记录用户纵坐标索引
                    matrix[i][j] = null;// 将该索引下的刚体对象清除
                } else if (rb instanceof Box) {
                    Box box = new Box(i, j);// 创建对应该横纵坐标索引的箱子对象
                    boxs.add(box);// 箱子列表保存此箱子
                    matrix[i][j] = null;// 将该索引下的刚体对象清除
                }
            }
        }
    }
    /**
     * 获取地图中的玩家对象
     * 
     * @return
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * 获取地图中的箱子列表
     * 
     * @return
     */
    public ArrayList<Box> getBoxs() {
        return boxs;
    }

}
