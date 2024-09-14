package com.mr.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.mr.util.GameImageUtil;
/**
 * ��ʼ���
 * 
 * @author mingrisoft
 *
 */
public class StarPanel extends JPanel implements KeyListener {
    BufferedImage image;// �������ʾ��ͼƬ
    Graphics2D g2;// ͼƬ��ͼ����
    MainFrame frame;// ������
    int x = 160;// ͼ��ĺ�����
    int y;// ͼ���������
    final int y1 = 320;// ��һ��ѡ���������
    final int y2 = 420;// �ڶ���ѡ���������

    public StarPanel(MainFrame frame) {
        this.frame = frame;
        this.frame.addKeyListener(this);
        this.frame.setFocusable(true);
        // ͼƬʹ��600*600�Ĳ�ͼ
        image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();
        this.frame.setTitle("������");
        y = y1;// Ĭ��ѡ���һ��ѡ��
    }

    @Override
    public void paint(Graphics g) {
        paintImage();// ����ͼƬ
        g.drawImage(image, 0, 0, this);// ��ͼƬ���������
    }
    /**
     * ����ͼƬ
     */
    private void paintImage() {
        g2.drawImage(GameImageUtil.backgroundImage, 0, 0, this);
        g2.setColor(Color.BLACK);// ʹ�ú�ɫ
        g2.setFont(new Font("����", Font.BOLD, 40));// ����
        g2.drawString("��ʼ��Ϸ", 230, y1 + 30);// ���Ƶ�һ��ѡ�������
        g2.drawString("��ͼ�༭��", 230, y2 + 30);// ���Ƶڶ���ѡ�������
        g2.drawImage(GameImageUtil.playerImage, x, y, this);// �����ͼƬ��Ϊѡ��ͼ��
    }

    /**
     * �����̰���ʱ
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();// ��ȡ�����ı���
        switch (key) {// �жϰ���
            case KeyEvent.VK_ENTER :// ����ǻس���
                switch (y) {// �ж�ͼ�������
                    case y1 :// ���ѡ�е�һ��ѡ��
                        frame.removeKeyListener(this);// ɾ����ǰ�����¼�
                        frame.setPanel(new GamePanel(frame, 0));// ������Ϸ���
                        break;
                    case y2 :// ���ѡ�еڶ���ѡ��
                        frame.removeKeyListener(this);// ɾ����ǰ�����¼�
                        frame.setPanel(new MapEditPanel(frame));// �����ͼ�༭�����
                        break;
                }
                break;
            case KeyEvent.VK_UP :// ������ϼ�ͷ���������¼�ͷһ�����߼�
            case KeyEvent.VK_DOWN :// ������¼�ͷ��
                if (y == y1) {// ���ͼ��ѡ�е�һ��ѡ��
                    y = y2;// ����ѡ�еڶ���ѡ��
                } else {
                    y = y1;// �������ֵ�һ��ѡ��
                }
                break;
        }
        repaint();// �ػ����
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
