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
 * 地图编辑器面板
 * 
 * @author mingrisoft
 *
 */
public class MapEditPanel extends JPanel {
    private MainFrame frame;// 主窗体绘图面板
    private BufferedImage image;// 绘制地图的主图片
    private Graphics2D g2;// 地图图片的绘图对象
    private DrawMapPanel editPanel;// 绘制面板
    private JToggleButton wall;// 墙按钮，可显示被选中状态
    private JToggleButton player;// 玩家按钮，可显示被选中状态
    private JToggleButton box;// 箱子按钮，可显示被选中状态
    private JToggleButton destination;// 目的地按钮，可显示被选中状态
    private JButton save, clear, back;// 保存按钮，清除按钮，返回按钮
    private RigidBody data[][];// 地图数据数组
    private int offsetX = 100, offsetY = 80;// 地图图片在绘制面板的横纵坐标偏移量

    /**
     * 地图编辑器构造方法
     */
    public MapEditPanel(MainFrame frame) {
        this.frame = frame;
        this.frame.setTitle("地图编辑器(鼠标左键绘图，鼠标右键擦除)");
        init();// 组件初始化
        addListener();// 添加组件事件监听
    }

    /**
     * 组件初始化
     */
    private void init() {
        // 使用400*400的彩色图片,游戏画面为600*600，此处为游戏画面的缩放版
        image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();// 获取图片绘图对象
        editPanel = new DrawMapPanel();// 实例化绘制面板
        data = new RigidBody[20][20];// 地图数组与游戏中行列数保持一致
        setLayout(new BorderLayout());// 使用边界布局

        save = new JButton("保存");// 实例化按钮对象
        clear = new JButton("清空");
        back = new JButton("返回");
        wall = new JToggleButton("墙块");
        player = new JToggleButton("玩家");
        box = new JToggleButton("箱子");
        destination = new JToggleButton("目的地");
        wall.setSelected(true);// 墙按钮默认选中

        ButtonGroup group = new ButtonGroup();// 按钮组
        group.add(wall);// 按钮组添加墙按钮
        group.add(player);// 按钮组添加玩家按钮
        group.add(box);// 按钮组添加箱子按钮
        group.add(destination);// 按钮组添加目的地按钮

        FlowLayout flow = new FlowLayout();// 流布局
        flow.setHgap(20);// 水平间隔20像素
        JPanel buttonPanel = new JPanel(flow);// 创建按钮面板，采用流布局

        buttonPanel.add(wall);// 按钮面板依次添加按钮
        buttonPanel.add(player);
        buttonPanel.add(box);
        buttonPanel.add(destination);
        buttonPanel.add(clear);
        buttonPanel.add(save);
        buttonPanel.add(back);

        add(editPanel, BorderLayout.CENTER);// 绘图面板放在中央位置
        add(buttonPanel, BorderLayout.SOUTH);// 按钮面板放在南部
    }

    /**
     * 绘制图片
     */
    void paintImage() {
        g2.setColor(Color.WHITE);// 使用白色
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());// 填充一个覆盖整个图片的白色矩形
        for (int i = 0, ilength = data.length; i < ilength; i++) {// 遍历地图数据数组
            for (int j = 0, jlength = data[i].length; j < jlength; j++) {
                RigidBody rb = data[i][j];// 取出刚体对象
                if (rb != null) {// 如果不是null
                    Image image = rb.getImage();// 获取此刚体的图片
                    g2.drawImage(image, i * 20, j * 20, 20, 20, this);// 会制在主图片中
                }
            }
        }
    }

    /**
     * 添加组件事件监听
     */
    private void addListener() {
        frame.addMouseListener(editPanel);// 主窗体添加绘图面板中的鼠标事件
        frame.addMouseMotionListener(editPanel);// 主窗体添加绘图面板中的鼠标拖曳事件

        clear.addActionListener(e -> { // 点击清除按钮时
            data = new RigidBody[20][20];// 地图数据数组重新赋值
            repaint();// 重绘组件
        });

        save.addActionListener(e -> {// 点击保存按钮时
            String name = GameMapUtil.CUSTOM_MAP_NAME;// 地图名称为自定义地图名称
            // while (true) {
            // name = JOptionPane.showInputDialog(MapEditPanel.this,
            // "请输入地图名称");// 弹出输入对话框，并记录用户输入的值
            // if (name == null) {// 如果用户选择取消
            // return;// 结束方法
            // } else if ("".equals(name.trim())) {// 如果用户未输入任何有效值
            // continue;// 重新执行循环
            // } else {// 如果用户输入有效值
            // break;// 退出循环
            // }
            // }
            GameMapUtil.createMap(data, name);// 保存此地图的数据文件
            JOptionPane.showMessageDialog(MapEditPanel.this,
                    "自定义地图创建成功！请直接开始游戏。");// 弹出对话框提示创建成功
            back.doClick();// 触发返回按钮的点击事件
        });

        back.addActionListener(e -> {// 当点击返回按钮时
            frame.removeMouseListener(editPanel);// 主窗体删除绘图面板中的鼠标事件
            frame.removeMouseMotionListener(editPanel);// 主窗体删除绘图面板中的鼠标拖曳事件
            frame.setPanel(new StarPanel(frame));// 主窗体载入开始面板
        });
    }

    /**
     * 地图编辑器中的绘图面板子类
     * 
     *
     */
    class DrawMapPanel extends JPanel
            implements
                MouseListener,
                MouseMotionListener {
        boolean paintFlag = false;// 绘制墙体标志
        int clickButton;// 鼠标点击的按键

        /**
         * 重写绘图方法
         */
        @Override
        public void paint(Graphics g) {
            paintImage();// 绘制主图片
            g.setColor(Color.gray);// 使用灰色
            g.fillRect(0, 0, getWidth(), getHeight());// 绘制一个矩形填充整个面板
            g.drawImage(image, offsetX, offsetY, this);// 将主图片绘制到面板中
        }

        /**
         * 绘制刚体
         * 
         * @param e
         *            - 鼠标事件
         */
        private void drawRigid(MouseEvent e) {
            RigidBody rb;// 创建刚体对象
            // 创建鼠标是否在地图范围内标志，由于窗体压缩了绘图面板，所以有些数值需要微调
            boolean inMap = e.getX() > offsetX && e.getX() < 400 + offsetX
                    && e.getY() > offsetY + 20 && e.getY() < 400 + offsetY + 20;
            if (inMap && paintFlag) {// 如果鼠标在地图内的标志和绘制墙体标志都为true
                int x = (e.getX() - offsetX) / 20;// 计算鼠标所在区域的刚体的横坐标索引
                int y = (e.getY() - offsetY - 20) / 20;// 计算鼠标所在区域的刚体的纵坐标索引
                if (wall.isSelected()) {// 如果墙按钮被选中
                    rb = new Wall();// 刚体按照墙进行实例化
                } else if (player.isSelected()) {// 如果玩家按钮被选中
                    rb = new Player();// 刚体按照玩家进行实例化
                } else if (box.isSelected()) {// 如果箱子钮被选中
                    rb = new Box();// 刚体按照箱子进行实例化
                } else {// 如果目的地按钮被选中
                    rb = new Destination();// 刚体按照目的地进行实例化
                }
                if (clickButton == MouseEvent.BUTTON1) {// 如果按下的是鼠标左键
                    if (data[x][y] == null) {// 选中的位置没有任何刚体
                        data[x][y] = rb;// 填充选中按钮对应的刚体
                    }
                } else if (clickButton == MouseEvent.BUTTON3) {// 如果按下的是鼠标右键
                    data[x][y] = null;// 将选中的位置的对象清空
                }
                repaint();// 重绘组件
            }
        }

        /**
         * 鼠标拖曳时
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            drawRigid(e);// 绘制墙块
        }

        /**
         * 鼠标按键按下时
         */
        @Override
        public void mousePressed(MouseEvent e) {
            paintFlag = true;// 绘制墙体标志为true
            clickButton = e.getButton();// 记录当前按下的鼠标按键
            drawRigid(e);// 绘制墙块
        }

        /**
         * 鼠标按键抬起时
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            paintFlag = false;// 绘制墙体标志为false
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // 不实现此方法
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // 不实现此方法
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // 不实现此方法
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // 不实现此方法
        }
    }
}
