package com.mr.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.mr.model.Box;
import com.mr.model.Destination;
import com.mr.model.Player;
import com.mr.model.RigidBody;
import com.mr.model.Wall;
import com.mr.util.GameMapUtil;
/**
 * ��ͼ�༭�����
 * 
 * @author mingrisoft
 *
 */
public class MapEditPanel extends JPanel {
    private MainFrame frame;// �������ͼ���
    private BufferedImage image;// ���Ƶ�ͼ����ͼƬ
    private Graphics2D g2;// ��ͼͼƬ�Ļ�ͼ����
    private DrawMapPanel editPanel;// �������
    private JToggleButton wall;// ǽ��ť������ʾ��ѡ��״̬
    private JToggleButton player;// ��Ұ�ť������ʾ��ѡ��״̬
    private JToggleButton box;// ���Ӱ�ť������ʾ��ѡ��״̬
    private JToggleButton destination;// Ŀ�ĵذ�ť������ʾ��ѡ��״̬
    private JButton save, clear, back;// ���水ť�������ť�����ذ�ť
    private RigidBody data[][];// ��ͼ��������
    private int offsetX = 100, offsetY = 80;// ��ͼͼƬ�ڻ������ĺ�������ƫ����

    /**
     * ��ͼ�༭�����췽��
     */
    public MapEditPanel(MainFrame frame) {
        this.frame = frame;
        this.frame.setTitle("��ͼ�༭��(��������ͼ������Ҽ�����)");
        init();// �����ʼ��
        addListener();// �������¼�����
    }

    /**
     * �����ʼ��
     */
    private void init() {
        // ʹ��400*400�Ĳ�ɫͼƬ,��Ϸ����Ϊ600*600���˴�Ϊ��Ϸ��������Ű�
        image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();// ��ȡͼƬ��ͼ����
        editPanel = new DrawMapPanel();// ʵ�����������
        data = new RigidBody[20][20];// ��ͼ��������Ϸ������������һ��
        setLayout(new BorderLayout());// ʹ�ñ߽粼��

        save = new JButton("����");// ʵ������ť����
        clear = new JButton("���");
        back = new JButton("����");
        wall = new JToggleButton("ǽ��");
        player = new JToggleButton("���");
        box = new JToggleButton("����");
        destination = new JToggleButton("Ŀ�ĵ�");
        wall.setSelected(true);// ǽ��ťĬ��ѡ��

        ButtonGroup group = new ButtonGroup();// ��ť��
        group.add(wall);// ��ť�����ǽ��ť
        group.add(player);// ��ť�������Ұ�ť
        group.add(box);// ��ť��������Ӱ�ť
        group.add(destination);// ��ť�����Ŀ�ĵذ�ť

        FlowLayout flow = new FlowLayout();// ������
        flow.setHgap(20);// ˮƽ���20����
        JPanel buttonPanel = new JPanel(flow);// ������ť��壬����������

        buttonPanel.add(wall);// ��ť���������Ӱ�ť
        buttonPanel.add(player);
        buttonPanel.add(box);
        buttonPanel.add(destination);
        buttonPanel.add(clear);
        buttonPanel.add(save);
        buttonPanel.add(back);

        add(editPanel, BorderLayout.CENTER);// ��ͼ����������λ��
        add(buttonPanel, BorderLayout.SOUTH);// ��ť�������ϲ�
    }

    /**
     * ����ͼƬ
     */
    void paintImage() {
        g2.setColor(Color.WHITE);// ʹ�ð�ɫ
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());// ���һ����������ͼƬ�İ�ɫ����
        for (int i = 0, ilength = data.length; i < ilength; i++) {// ������ͼ��������
            for (int j = 0, jlength = data[i].length; j < jlength; j++) {
                RigidBody rb = data[i][j];// ȡ���������
                if (rb != null) {// �������null
                    Image image = rb.getImage();// ��ȡ�˸����ͼƬ
                    g2.drawImage(image, i * 20, j * 20, 20, 20, this);// ��������ͼƬ��
                }
            }
        }
    }

    /**
     * �������¼�����
     */
    private void addListener() {
        frame.addMouseListener(editPanel);// ��������ӻ�ͼ����е�����¼�
        frame.addMouseMotionListener(editPanel);// ��������ӻ�ͼ����е������ҷ�¼�

        clear.addActionListener(e -> { // ��������ťʱ
            data = new RigidBody[20][20];// ��ͼ�����������¸�ֵ
            repaint();// �ػ����
        });

        save.addActionListener(e -> {// ������水ťʱ
            String name = GameMapUtil.CUSTOM_MAP_NAME;// ��ͼ����Ϊ�Զ����ͼ����
            // while (true) {
            // name = JOptionPane.showInputDialog(MapEditPanel.this,
            // "�������ͼ����");// ��������Ի��򣬲���¼�û������ֵ
            // if (name == null) {// ����û�ѡ��ȡ��
            // return;// ��������
            // } else if ("".equals(name.trim())) {// ����û�δ�����κ���Чֵ
            // continue;// ����ִ��ѭ��
            // } else {// ����û�������Чֵ
            // break;// �˳�ѭ��
            // }
            // }
            GameMapUtil.createMap(data, name);// ����˵�ͼ�������ļ�
            JOptionPane.showMessageDialog(MapEditPanel.this,
                    "�Զ����ͼ�����ɹ�����ֱ�ӿ�ʼ��Ϸ��");// �����Ի�����ʾ�����ɹ�
            back.doClick();// �������ذ�ť�ĵ���¼�
        });

        back.addActionListener(e -> {// ��������ذ�ťʱ
            frame.removeMouseListener(editPanel);// ������ɾ����ͼ����е�����¼�
            frame.removeMouseMotionListener(editPanel);// ������ɾ����ͼ����е������ҷ�¼�
            frame.setPanel(new StarPanel(frame));// ���������뿪ʼ���
        });
    }

    /**
     * ��ͼ�༭���еĻ�ͼ�������
     * 
     *
     */
    class DrawMapPanel extends JPanel
            implements
                MouseListener,
                MouseMotionListener {
        boolean paintFlag = false;// ����ǽ���־
        int clickButton;// ������İ���

        /**
         * ��д��ͼ����
         */
        @Override
        public void paint(Graphics g) {
            paintImage();// ������ͼƬ
            g.setColor(Color.gray);// ʹ�û�ɫ
            g.fillRect(0, 0, getWidth(), getHeight());// ����һ����������������
            g.drawImage(image, offsetX, offsetY, this);// ����ͼƬ���Ƶ������
        }

        /**
         * ���Ƹ���
         * 
         * @param e
         *            - ����¼�
         */
        private void drawRigid(MouseEvent e) {
            RigidBody rb;// �����������
            // ��������Ƿ��ڵ�ͼ��Χ�ڱ�־�����ڴ���ѹ���˻�ͼ��壬������Щ��ֵ��Ҫ΢��
            boolean inMap = e.getX() > offsetX && e.getX() < 400 + offsetX
                    && e.getY() > offsetY + 20 && e.getY() < 400 + offsetY + 20;
            if (inMap && paintFlag) {// �������ڵ�ͼ�ڵı�־�ͻ���ǽ���־��Ϊtrue
                int x = (e.getX() - offsetX) / 20;// ���������������ĸ���ĺ���������
                int y = (e.getY() - offsetY - 20) / 20;// ���������������ĸ��������������
                if (wall.isSelected()) {// ���ǽ��ť��ѡ��
                    rb = new Wall();// ���尴��ǽ����ʵ����
                } else if (player.isSelected()) {// �����Ұ�ť��ѡ��
                    rb = new Player();// ���尴����ҽ���ʵ����
                } else if (box.isSelected()) {// �������ť��ѡ��
                    rb = new Box();// ���尴�����ӽ���ʵ����
                } else {// ���Ŀ�ĵذ�ť��ѡ��
                    rb = new Destination();// ���尴��Ŀ�ĵؽ���ʵ����
                }
                if (clickButton == MouseEvent.BUTTON1) {// ������µ���������
                    if (data[x][y] == null) {// ѡ�е�λ��û���κθ���
                        data[x][y] = rb;// ���ѡ�а�ť��Ӧ�ĸ���
                    }
                } else if (clickButton == MouseEvent.BUTTON3) {// ������µ�������Ҽ�
                    data[x][y] = null;// ��ѡ�е�λ�õĶ������
                }
                repaint();// �ػ����
            }
        }

        /**
         * �����ҷʱ
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            drawRigid(e);// ����ǽ��
        }

        /**
         * ��갴������ʱ
         */
        @Override
        public void mousePressed(MouseEvent e) {
            paintFlag = true;// ����ǽ���־Ϊtrue
            clickButton = e.getButton();// ��¼��ǰ���µ���갴��
            drawRigid(e);// ����ǽ��
        }

        /**
         * ��갴��̧��ʱ
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            paintFlag = false;// ����ǽ���־Ϊfalse
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // ��ʵ�ִ˷���
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // ��ʵ�ִ˷���
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // ��ʵ�ִ˷���
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // ��ʵ�ִ˷���
        }
    }
}
