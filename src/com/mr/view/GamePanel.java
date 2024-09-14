package com.mr.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mr.model.Box;
import com.mr.model.Destination;
import com.mr.model.Map;
import com.mr.model.Player;
import com.mr.model.RigidBody;
import com.mr.model.Wall;
import com.mr.util.GameMapUtil;

/**
 * ��Ϸ���
 *
 * @author mingrisoft
 */
public class GamePanel extends JPanel implements KeyListener {
    private MainFrame frame;// ������
    private int level;// �ؿ����
    private RigidBody[][] data = new RigidBody[20][20];// ��ͼ����
    private BufferedImage image;// ��ͼƬ
    private Graphics2D g2;// ��ͼƬ�Ļ�ͼ����
    private Player player;// ���
    private ArrayList<Box> boxs;// �����б�

    /**
     * ���췽������Ϸ��ȡ�ؿ���Ŷ�Ӧ�ĵ�ͼ������ؿ����С��1�����ȡ�Զ����ͼ
     *
     * @param frame - ������
     * @param level - �ؿ����
     */
    public GamePanel(MainFrame frame, int level) {
        this.frame = frame;
        this.level = level;

        Map map;// ��Ϸ��ͼ����
        if (level < 1) {// ����ؿ���С��1
            map = GameMapUtil.readCustomMap();// ��ȡ�Զ����ͼ����
            this.level = 0;// �ؿ�����Ϊ0�����㶨����һ��
            if (map == null) {// ���û���Զ����ͼ�ļ�
                this.level = 1;// �ؿ���Ϊ��һ��
                map = GameMapUtil.readMap(1);// ��ʼ��һ��
            }
        } else {// ����ؿ�������1
            map = GameMapUtil.readMap(level);// ��ȡ��Ӧ�ؿ����ĵ�ͼ
        }

        data = map.getMapData();// ��ȡ��ͼ��ĵ�ͼ��������
        player = map.getPlayer();// ��ȡ��ͼ����Ҷ���
        boxs = map.getBoxs();// ��ȡ��ͼ�������б�

        // ��ͼƬΪ600*600�Ĳ�ͼ
        image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();// ��ȡ��ͼƬ��ͼ����

        this.frame.addKeyListener(this);// ��������Ӽ��̼���
        this.frame.setFocusable(true);// �������ȡ����
        this.frame.setTitle("������(��esc���¿�ʼ)");// �޸����������
    }

    /**
     * ��д���Ʒ���
     */
    @Override
    public void paint(Graphics g) {
        paintImage();// ������ͼƬ
        g.drawImage(image, 0, 0, this);// ����ͼƬ���Ƶ������

        boolean finish = true;// �����ж���Ϸ�Ƿ�����ı�־
        for (Box box : boxs) {// ���������б�
            finish &= box.isArrived();// ������־�����ӵ���״̬��������
        }
        if (finish && boxs.size() > 0) {// ����������Ӷ�����Ŀ�ĵ������ӵĸ�������0
            gotoAnotherLevel(level + 1);// ������һ��
        }
    }

    /**
     * ������ͼƬ
     */
    private void paintImage() {
        g2.setColor(Color.WHITE);// ʹ�ð�ɫ
        g2.fillRect(0, 0, getWidth(), getHeight());// ����һ�������������ͼƬ

        for (int i = 0, ilength = data.length; i < ilength; i++) {// ������ͼ��������
            for (int j = 0, jlength = data[i].length; j < jlength; j++) {
                RigidBody rb = data[i][j];// ��ȡ�������
                if (rb != null) {
                    Image image = rb.getImage();// ��ȡ����ͼƬ
                    g2.drawImage(image, i * 30, j * 30, 30, 30, this);// �ڶ�Ӧλ�û��Ƹ���ͼƬ
                }
            }
        }
        for (Box box : boxs) {// ���������б�
            // �ڶ�Ӧλ�û�������ͼƬ
            g2.drawImage(box.getImage(), box.x * 30, box.y * 30, 30, 30, this);
        }
        // �������ͼƬ
        g2.drawImage(player.getImage(), player.x * 30, player.y * 30, 30, 30,
                this);
    }

    /**
     * ���������ؿ�
     *
     * @param level - �ؿ���
     */
    private void gotoAnotherLevel(int level) {
        frame.removeKeyListener(this);// ������ɾ������ʵ�ֵļ����¼�
        // �����̣߳�����Runnable�ӿڵ�������
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500);// 0.5��֮��
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (level > GameMapUtil.getLevelCount()) {// �������Ĺؿ����������ؿ���
                frame.setPanel(new StarPanel(frame));// ���뿪ʼ���
                JOptionPane.showMessageDialog(frame, "ͨ������");// ����ͨ�ضԻ���
            } else {
                frame.setPanel(new GamePanel(frame, level));// �����Ӧ�ؿ�
            }
        });
        t.start();// �����߳�
    }

    /**
     * ���̰�������ʱ
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();// ��ȡ���µİ���ֵ
        int x = player.x;// ��¼��Һ���������
        int y = player.y;// ��¼�������������
        switch (key) {// �жϰ���ֵ
            case KeyEvent.VK_UP:// ������µ����ϼ�ͷ����
                moveThePlayer(x, y - 1, x, y - 2);
                break;
            case KeyEvent.VK_DOWN:// ������µ����¼�ͷ����
                moveThePlayer(x, y + 1, x, y + 2);
                break;
            case KeyEvent.VK_LEFT:// ������µ������ͷ����
                moveThePlayer(x - 1, y, x - 2, y);
                break;
            case KeyEvent.VK_RIGHT:// ������µ����Ҽ�ͷ����
                moveThePlayer(x + 1, y, x + 2, y);
                break;
            case KeyEvent.VK_ESCAPE:// �����Esc��
                gotoAnotherLevel(level);// ���¿�ʼ������Ϸ
                break;
        }
        repaint();// �ػ����
    }

    /**
     * �ƶ���ҽ�ɫ
     *
     * @param xNext1 - ����ƶ�һ���������Ŀ��λ�õĺ���������
     * @param yNext1 - ����ƶ�һ���������Ŀ��λ�õ�����������
     * @param xNext2 - ����ƶ������������Ŀ��λ�õĺ����������������ж����ӱ��Ƶ���λ��
     * @param yNext2 - ����ƶ������������Ŀ��λ�õ������������������ж����ӱ��Ƶ���λ��
     */
    private void moveThePlayer(int xNext1, int yNext1, int xNext2, int yNext2) {
        if (data[xNext1][yNext1] instanceof Wall) {// ������ǰ����ǽ
            return;// ʲô������
        }
        Box box = new Box(xNext1, yNext1);// ���û�ǰ��λ�ô������Ӷ���
        if (boxs.contains(box)) {// �����������������б����Ǵ��ڵ�
            int index = boxs.indexOf(box);// ��ȡ���������б��е�����
            box = boxs.get(index);// ȡ���б��и����ӵĶ���
            if (data[xNext2][yNext2] instanceof Wall) {// ������ӵ�ǰ����ǽ
                return;// ʲô������
            }
            if (boxs.contains(new Box(xNext2, yNext2))) {// ������ӵ�ǰ��������������
                return;// ʲô������
            }
            if (data[xNext2][yNext2] instanceof Destination) {// ������ӵ�ǰ����Ŀ�ĵ�
                box.arrive();// ���ӵ���
            } else if (box.isArrived()) {// ������Ӿ���Ŀ�ĵ���
                box.leave();// �����뿪
            }
            box.x = xNext2;// ���ӱ�����Ƶ�����λ��
            box.y = yNext2;
        }
        player.x = xNext1;// ����ƶ�����λ��
        player.y = yNext1;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ��ʵ�ִ˷���
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ��ʵ�ִ˷���
    }
}
