package com.mr.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * ��ͼ�����ࡣ
 * 
 * @author mingrisoft
 */
public class GameImageUtil {

    private static final String IMAGE_PATH = "src/com/mr/image";// ͼƬ��ŵ�·��
    public static BufferedImage playerImage;// ���ͼƬ
    public static BufferedImage boxImage1;// ����δ����Ŀ�ĵ�ͼƬ
    public static BufferedImage boxImage2;// �����ѵ���Ŀ�ĵ�ͼƬ
    public static BufferedImage wallImage;// ǽͼƬ
    public static BufferedImage destinationImage;// Ŀ�ĵ�ͼƬ
    public static BufferedImage backgroundImage;// ����ͼƬ

    static {// ��ͼƬ���г�ʼ��
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
