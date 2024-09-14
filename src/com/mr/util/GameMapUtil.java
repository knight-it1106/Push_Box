package com.mr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mr.model.Box;
import com.mr.model.Destination;
import com.mr.model.Map;
import com.mr.model.Player;
import com.mr.model.RigidBody;
import com.mr.model.Wall;

/**
 * 地图数据工具类。
 * 
 * @author mingrisoft
 */
public class GameMapUtil {
    private static final char NULL_CODE = '0';// 空白区域使用的占位符
    private static final char WALL_CODE = '1';// 墙使用的占位符
    private static final char BOX_CODE = '2';// 箱子使用的占位符
    private static final char PLAYER_CODE = '3';// 玩家使用的占位符
    private static final char DESTINATION_CODE = '4';// 目的地使用的占位符
    private static final String MAP_PATH = "src/com/mr/map";// 地图存放的路径
    public static final String CUSTOM_MAP_NAME = "custom";// 自定义地图名称

    /**
     * 创建地图文件
     * 
     * @param arr
     *            - 地图数据
     * @param mapName
     *            - 地图文件名称
     */
    static public void createMap(RigidBody[][] arr, String mapName) {
        StringBuilder data = new StringBuilder();// 地图文件将要写入的内容
        for (int i = 0, ilength = arr.length; i < ilength; i++) {
            for (int j = 0, jlength = arr[i].length; j < jlength; j++) {
                RigidBody rb = arr[i][j];// 获取地图数据中的刚体对象
                if (rb == null) {// 如果是空对象
                    data.append(NULL_CODE);// 拼接空白区域的占位符
                } else if (rb instanceof Wall) {// 如果是墙
                    data.append(WALL_CODE);// 拼接墙的占位符
                } else if (rb instanceof Player) {// 如果是玩家
                    data.append(PLAYER_CODE);// 拼接玩家的占位符
                } else if (rb instanceof Box) {// 如果是箱子
                    data.append(BOX_CODE);// 拼接箱子的占位符
                } else if (rb instanceof Destination) {// 如果是目的地
                    data.append(DESTINATION_CODE);// 拼接目的地的占位符
                }
            }
            data.append("\n");// 拼接换行
        }
        File mapFile = new File(MAP_PATH, mapName);// 创建地图文件对象
        // 开始文件输出流
        try (FileOutputStream fos = new FileOutputStream(mapFile);) {
            fos.write(data.toString().getBytes());// 将字符串写入到文件
            fos.flush();// 刷新输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取自定义地图文件
     * 
     * @return 自定义地图对象
     */
    public static Map readCustomMap() {
        File f = new File(MAP_PATH, CUSTOM_MAP_NAME);// 创建定义地图文件对象
        if (!f.exists()) {// 如果文件不存在
            return null;
        }
        Map map = readMap(CUSTOM_MAP_NAME);// 读取自定义文件中的数据
        return map;// 返回自定义地图对象
    }
    /**
     * 清除自定义地图文件
     */
    public static void clearCustomMap() {
        File f = new File(MAP_PATH, CUSTOM_MAP_NAME);// 创建定义地图文件对象
        if (f.exists()) {// 如果文件存在
            f.delete();// 删除
        }
    }

    /**
     * 读取地图文件数据
     * 
     * @param mapNum
     *            - 地图文件编号
     * @return 地图文件对象
     */
    public static Map readMap(int mapNum) {
        // 将数字转为字符串，调用重载方法
        return readMap(String.valueOf(mapNum));
    }

    /**
     * 读取地图文件数据
     * 
     * @param mapName
     *            - 地图文件名称
     * @return 地图文件对象
     */
    public static Map readMap(String mapName) {
        File f = new File(MAP_PATH, mapName);// 获取地图文件对象
        if (!f.exists()) {// 如果文件不存在
            System.err.println("地图不存在:" + mapName);
            return null;
        }
        RigidBody[][] data = new RigidBody[20][20];// 地图数据数组
        // 开启缓冲输入流
        try (FileInputStream fis = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)) {
            String tmp = null;// 读取一行数据的临时字符串
            int row = 0;// 当前读取的行数
            while ((tmp = br.readLine()) != null) {// 循环读出一行有内容的字符串
                char codes[] = tmp.toCharArray();// 读出的字符串拆分成字符数组
                // 循环字符数组，并保证读取的行数不超过20
                for (int i = 0; i < codes.length && row < 20; i++) {
                    RigidBody rb = null;// 准备保存到地图数组中的刚体对象
                    switch (codes[i]) {// 判断读出的字符
                        case WALL_CODE :// 如果是墙的占位符
                            rb = new Wall();// 刚体以墙的形式实例化
                            break;
                        case BOX_CODE :// 如果是箱子的占位符
                            rb = new Box();// 刚体以箱子的形式实例化
                            break;
                        case PLAYER_CODE :// 如果是玩家的占位符
                            rb = new Player();// 刚体以玩家的形式实例化
                            break;
                        case DESTINATION_CODE :// 如果是目的地的占位符
                            rb = new Destination();// 刚体以目的地的形式实例化
                            break;
                    }
                    data[row][i] = rb;// 刚体对象保存到地图数组中
                }
                row++;// 读取的行数递增
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Map(data);// 返回包含此地图数据的地图对象
    }

    /**
     * 获取总关卡数
     * 
     * @return
     */
    public static int getLevelCount() {
        File dir = new File(MAP_PATH);// 读取地图存放路径
        if (dir.exists()) {// 如果该路径是文件夹
            File maps[] = dir.listFiles();// 获取该文件夹下的所有文件
            for (File f : maps) {// 遍历这些文件
                if (CUSTOM_MAP_NAME.equals(f.getName())) {// 如果存在自定义地图
                    return maps.length - 1;// 总关卡数 = 文件数 -1
                }
            }
            return maps.length;// 总关卡数 = 文件数
        }
        return 0;// 没有关卡
    }
}
