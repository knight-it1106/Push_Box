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
 * 游戏面板
 *
 * @author mingrisoft
 */
public class GamePanel extends JPanel implements KeyListener {
    private MainFrame frame;// 主窗体
    private int level;// 关卡编号
    private RigidBody[][] data = new RigidBody[20][20];// 地图数据
    private BufferedImage image;// 主图片
    private Graphics2D g2;// 主图片的绘图对象
    private Player player;// 玩家
    private ArrayList<Box> boxs;// 箱子列表

    /**
     * 构造方法。游戏读取关卡编号对应的地图，如果关卡编号小于1，则读取自定义地图
     *
     * @param frame - 主窗体
     * @param level - 关卡编号
     */
    public GamePanel(MainFrame frame, int level) {
        this.frame = frame;
        this.level = level;

        Map map;// 游戏地图对象
        if (level < 1) {// 如果关卡数小于1
            map = GameMapUtil.readCustomMap();// 读取自定义地图对象
            this.level = 0;// 关卡数设为0，方便定义下一关
            if (map == null) {// 如果没有自定义地图文件
                this.level = 1;// 关卡设为第一关
                map = GameMapUtil.readMap(1);// 开始第一关
            }
        } else {// 如果关卡数大于1
            map = GameMapUtil.readMap(level);// 读取对应关卡数的地图
        }

        data = map.getMapData();// 获取地图里的地图数据数组
        player = map.getPlayer();// 获取地图中玩家对象
        boxs = map.getBoxs();// 获取地图中箱子列表

        // 主图片为600*600的彩图
        image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();// 获取主图片绘图对象

        this.frame.addKeyListener(this);// 主窗体添加键盘监听
        this.frame.setFocusable(true);// 主窗体获取焦点
        this.frame.setTitle("推箱子(按esc重新开始)");// 修改主窗体标题
    }

    /**
     * 重写绘制方法
     */
    @Override
    public void paint(Graphics g) {
        paintImage();// 绘制主图片
        g.drawImage(image, 0, 0, this);// 将主图片绘制到面板中

        boolean finish = true;// 用于判断游戏是否结束的标志
        for (Box box : boxs) {// 遍历箱子列表
            finish &= box.isArrived();// 结束标志与箱子到达状态做与运算
        }
        if (finish && boxs.size() > 0) {// 如果所有箱子都到达目的地且箱子的个数大于0
            gotoAnotherLevel(level + 1);// 进入下一关
        }
    }

    /**
     * 绘制主图片
     */
    private void paintImage() {
        g2.setColor(Color.WHITE);// 使用白色
        g2.fillRect(0, 0, getWidth(), getHeight());// 绘制一个矩形填充整个图片

        for (int i = 0, ilength = data.length; i < ilength; i++) {// 遍历地图数据数组
            for (int j = 0, jlength = data[i].length; j < jlength; j++) {
                RigidBody rb = data[i][j];// 获取刚体对象
                if (rb != null) {
                    Image image = rb.getImage();// 获取刚体图片
                    g2.drawImage(image, i * 30, j * 30, 30, 30, this);// 在对应位置绘制刚体图片
                }
            }
        }
        for (Box box : boxs) {// 遍历箱子列表
            // 在对应位置绘制箱子图片
            g2.drawImage(box.getImage(), box.x * 30, box.y * 30, 30, 30, this);
        }
        // 绘制玩家图片
        g2.drawImage(player.getImage(), player.x * 30, player.y * 30, 30, 30,
                this);
    }

    /**
     * 进入其他关卡
     *
     * @param level - 关卡数
     */
    private void gotoAnotherLevel(int level) {
        frame.removeKeyListener(this);// 主窗体删除本类实现的键盘事件
        // 创建线程，创建Runnable接口的匿名类
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500);// 0.5秒之后
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (level > GameMapUtil.getLevelCount()) {// 如果传入的关卡数大于最大关卡数
                frame.setPanel(new StarPanel(frame));// 进入开始面板
                JOptionPane.showMessageDialog(frame, "通关啦！");// 弹出通关对话框
            } else {
                frame.setPanel(new GamePanel(frame, level));// 进入对应关卡
            }
        });
        t.start();// 启动线程
    }

    /**
     * 键盘按键按下时
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();// 获取按下的按键值
        int x = player.x;// 记录玩家横坐标索引
        int y = player.y;// 记录玩家纵坐标索引
        switch (key) {// 判断按键值
            case KeyEvent.VK_UP:// 如果按下的是上箭头按键
                moveThePlayer(x, y - 1, x, y - 2);
                break;
            case KeyEvent.VK_DOWN:// 如果按下的是下箭头按键
                moveThePlayer(x, y + 1, x, y + 2);
                break;
            case KeyEvent.VK_LEFT:// 如果按下的是左箭头按键
                moveThePlayer(x - 1, y, x - 2, y);
                break;
            case KeyEvent.VK_RIGHT:// 如果按下的是右箭头按键
                moveThePlayer(x + 1, y, x + 2, y);
                break;
            case KeyEvent.VK_ESCAPE:// 如果按Esc键
                gotoAnotherLevel(level);// 重新开始本局游戏
                break;
        }
        repaint();// 重绘面板
    }

    /**
     * 移动玩家角色
     *
     * @param xNext1 - 玩家移动一次所到达的目标位置的横坐标索引
     * @param yNext1 - 玩家移动一次所到达的目标位置的纵坐标索引
     * @param xNext2 - 玩家移动两次所到达的目标位置的横坐标索引，用于判断箱子被推到的位置
     * @param yNext2 - 玩家移动两次所到达的目标位置的纵坐标索引，用于判断箱子被推到的位置
     */
    private void moveThePlayer(int xNext1, int yNext1, int xNext2, int yNext2) {
        if (data[xNext1][yNext1] instanceof Wall) {// 如果玩家前方是墙
            return;// 什么都不做
        }
        Box box = new Box(xNext1, yNext1);// 在用户前方位置创建箱子对象
        if (boxs.contains(box)) {// 如果这个箱子在箱子列表中是存在的
            int index = boxs.indexOf(box);// 获取该箱子在列表中的索引
            box = boxs.get(index);// 取出列表中该箱子的对象
            if (data[xNext2][yNext2] instanceof Wall) {// 如果箱子的前方是墙
                return;// 什么都不做
            }
            if (boxs.contains(new Box(xNext2, yNext2))) {// 如果箱子的前方还有其他箱子
                return;// 什么都不做
            }
            if (data[xNext2][yNext2] instanceof Destination) {// 如果箱子的前方是目的地
                box.arrive();// 箱子到达
            } else if (box.isArrived()) {// 如果箱子就在目的地上
                box.leave();// 箱子离开
            }
            box.x = xNext2;// 箱子被玩家推到了新位置
            box.y = yNext2;
        }
        player.x = xNext1;// 玩家移动到新位置
        player.y = yNext1;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 不实现此方法
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 不实现此方法
    }
}
