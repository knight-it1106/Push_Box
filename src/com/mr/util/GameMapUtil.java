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
 * ��ͼ���ݹ����ࡣ
 * 
 * @author mingrisoft
 */
public class GameMapUtil {
    private static final char NULL_CODE = '0';// �հ�����ʹ�õ�ռλ��
    private static final char WALL_CODE = '1';// ǽʹ�õ�ռλ��
    private static final char BOX_CODE = '2';// ����ʹ�õ�ռλ��
    private static final char PLAYER_CODE = '3';// ���ʹ�õ�ռλ��
    private static final char DESTINATION_CODE = '4';// Ŀ�ĵ�ʹ�õ�ռλ��
    private static final String MAP_PATH = "src/com/mr/map";// ��ͼ��ŵ�·��
    public static final String CUSTOM_MAP_NAME = "custom";// �Զ����ͼ����

    /**
     * ������ͼ�ļ�
     * 
     * @param arr
     *            - ��ͼ����
     * @param mapName
     *            - ��ͼ�ļ�����
     */
    static public void createMap(RigidBody[][] arr, String mapName) {
        StringBuilder data = new StringBuilder();// ��ͼ�ļ���Ҫд�������
        for (int i = 0, ilength = arr.length; i < ilength; i++) {
            for (int j = 0, jlength = arr[i].length; j < jlength; j++) {
                RigidBody rb = arr[i][j];// ��ȡ��ͼ�����еĸ������
                if (rb == null) {// ����ǿն���
                    data.append(NULL_CODE);// ƴ�ӿհ������ռλ��
                } else if (rb instanceof Wall) {// �����ǽ
                    data.append(WALL_CODE);// ƴ��ǽ��ռλ��
                } else if (rb instanceof Player) {// ��������
                    data.append(PLAYER_CODE);// ƴ����ҵ�ռλ��
                } else if (rb instanceof Box) {// ���������
                    data.append(BOX_CODE);// ƴ�����ӵ�ռλ��
                } else if (rb instanceof Destination) {// �����Ŀ�ĵ�
                    data.append(DESTINATION_CODE);// ƴ��Ŀ�ĵص�ռλ��
                }
            }
            data.append("\n");// ƴ�ӻ���
        }
        File mapFile = new File(MAP_PATH, mapName);// ������ͼ�ļ�����
        // ��ʼ�ļ������
        try (FileOutputStream fos = new FileOutputStream(mapFile);) {
            fos.write(data.toString().getBytes());// ���ַ���д�뵽�ļ�
            fos.flush();// ˢ�������
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ�Զ����ͼ�ļ�
     * 
     * @return �Զ����ͼ����
     */
    public static Map readCustomMap() {
        File f = new File(MAP_PATH, CUSTOM_MAP_NAME);// ���������ͼ�ļ�����
        if (!f.exists()) {// ����ļ�������
            return null;
        }
        Map map = readMap(CUSTOM_MAP_NAME);// ��ȡ�Զ����ļ��е�����
        return map;// �����Զ����ͼ����
    }
    /**
     * ����Զ����ͼ�ļ�
     */
    public static void clearCustomMap() {
        File f = new File(MAP_PATH, CUSTOM_MAP_NAME);// ���������ͼ�ļ�����
        if (f.exists()) {// ����ļ�����
            f.delete();// ɾ��
        }
    }

    /**
     * ��ȡ��ͼ�ļ�����
     * 
     * @param mapNum
     *            - ��ͼ�ļ����
     * @return ��ͼ�ļ�����
     */
    public static Map readMap(int mapNum) {
        // ������תΪ�ַ������������ط���
        return readMap(String.valueOf(mapNum));
    }

    /**
     * ��ȡ��ͼ�ļ�����
     * 
     * @param mapName
     *            - ��ͼ�ļ�����
     * @return ��ͼ�ļ�����
     */
    public static Map readMap(String mapName) {
        File f = new File(MAP_PATH, mapName);// ��ȡ��ͼ�ļ�����
        if (!f.exists()) {// ����ļ�������
            System.err.println("��ͼ������:" + mapName);
            return null;
        }
        RigidBody[][] data = new RigidBody[20][20];// ��ͼ��������
        // ��������������
        try (FileInputStream fis = new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)) {
            String tmp = null;// ��ȡһ�����ݵ���ʱ�ַ���
            int row = 0;// ��ǰ��ȡ������
            while ((tmp = br.readLine()) != null) {// ѭ������һ�������ݵ��ַ���
                char codes[] = tmp.toCharArray();// �������ַ�����ֳ��ַ�����
                // ѭ���ַ����飬����֤��ȡ������������20
                for (int i = 0; i < codes.length && row < 20; i++) {
                    RigidBody rb = null;// ׼�����浽��ͼ�����еĸ������
                    switch (codes[i]) {// �ж϶������ַ�
                        case WALL_CODE :// �����ǽ��ռλ��
                            rb = new Wall();// ������ǽ����ʽʵ����
                            break;
                        case BOX_CODE :// ��������ӵ�ռλ��
                            rb = new Box();// ���������ӵ���ʽʵ����
                            break;
                        case PLAYER_CODE :// �������ҵ�ռλ��
                            rb = new Player();// ��������ҵ���ʽʵ����
                            break;
                        case DESTINATION_CODE :// �����Ŀ�ĵص�ռλ��
                            rb = new Destination();// ������Ŀ�ĵص���ʽʵ����
                            break;
                    }
                    data[row][i] = rb;// ������󱣴浽��ͼ������
                }
                row++;// ��ȡ����������
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Map(data);// ���ذ����˵�ͼ���ݵĵ�ͼ����
    }

    /**
     * ��ȡ�ܹؿ���
     * 
     * @return
     */
    public static int getLevelCount() {
        File dir = new File(MAP_PATH);// ��ȡ��ͼ���·��
        if (dir.exists()) {// �����·�����ļ���
            File maps[] = dir.listFiles();// ��ȡ���ļ����µ������ļ�
            for (File f : maps) {// ������Щ�ļ�
                if (CUSTOM_MAP_NAME.equals(f.getName())) {// ��������Զ����ͼ
                    return maps.length - 1;// �ܹؿ��� = �ļ��� -1
                }
            }
            return maps.length;// �ܹؿ��� = �ļ���
        }
        return 0;// û�йؿ�
    }
}
