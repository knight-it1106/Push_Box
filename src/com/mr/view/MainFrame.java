package com.mr.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mr.util.GameMapUtil;
/**
 * 主窗体
 * 
 * @author mingrisoft
 *
 */

public class MainFrame extends JFrame {
    private int width = 605;// 不可调整大小时的宽度，可调整大小宽度选择616
    private int height = 627;// 不可调整大小时的高度，可调整大小高度选择638

    public MainFrame() {
        setTitle("推箱子");// 设置标题
         setResizable(false);// 不可调整大小
        setSize(width, height);// 设置宽高
        Toolkit tool = Toolkit.getDefaultToolkit(); // 创建系统该默认组件工具包
        Dimension d = tool.getScreenSize(); // 获取屏幕尺寸，赋给一个二维坐标对象
        // 让主窗体在屏幕中间显示
        setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 点击关闭窗体不做任何操作
        addListener();// 添加监听
        setPanel(new StarPanel(this));// 载入开始面板
        GameMapUtil.clearCustomMap();// 删除自定义地图
    }
    /**
     * 更换主容器中的面板
     * 
     * @param panel
     *            - 更换的面板
     */
    
    public void setPanel(JPanel panel) {
        Container c = getContentPane();// 获取主容器对象
        c.removeAll();// 删除容器中所有组件
        c.add(panel, BorderLayout.CENTER);// 容器添加面板
        c.validate();// 容器重新验证所有组件
        c.repaint();
    }

    /**
     * 添加组件监听
     */
    private void addListener() {
        addWindowListener(new WindowAdapter() {// 添加窗体事件监听
            public void windowClosing(WindowEvent e) {// 窗体关闭时
                int closeCode = JOptionPane.showConfirmDialog(MainFrame.this,
                        "是否退出游戏？", "提示！", JOptionPane.YES_NO_OPTION);// 弹出选择对话框，并记录用户选择
                if (closeCode == JOptionPane.YES_OPTION) {// 如果用户选择确定
                    System.exit(0);// 关闭程序
                }
            }
        });
    }

}
