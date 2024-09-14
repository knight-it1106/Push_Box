package com.mr.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * 绘图工具类。
 * 
 * @author mingrisoft
 */
public class GameImageUtil {

    private static final String IMAGE_PATH = "src/com/mr/image";// 图片存放的路径
    public static BufferedImage playerImage;// 玩家图片
    public static BufferedImage boxImage1;// 箱子未到达目的地图片
    public static BufferedImage boxImage2;// 箱子已到达目的地图片
    public static BufferedImage wallImage;// 墙图片
    public static BufferedImage destinationImage;// 目的地图片
    public static BufferedImage backgroundImage;// 背景图片

    static {// 对图片进行初始化
        try {
            playerImage = ImageIO.read(new File(IMAGE_PATH, "player.png"));
            boxImage1 = ImageIO.read(new File(IMAGE_PATH, "box1.png"));
            boxImage2 = ImageIO.read(new File(IMAGE_PATH, "box2.png"));
            wallImage = ImageIO.read(new File(IMAGE_PATH, "wall.png"));
            destinationImage = ImageIO
                    .read(new File(IMAGE_PATH, "destination.png"));
            backgroundImage = ImageIO
                    .read(new File(IMAGE_PATH, "background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
