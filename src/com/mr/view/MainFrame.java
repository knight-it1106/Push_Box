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
 * ������
 * 
 * @author mingrisoft
 *
 */

public class MainFrame extends JFrame {
    private int width = 605;// ���ɵ�����Сʱ�Ŀ�ȣ��ɵ�����С���ѡ��616
    private int height = 627;// ���ɵ�����Сʱ�ĸ߶ȣ��ɵ�����С�߶�ѡ��638

    public MainFrame() {
        setTitle("������");// ���ñ���
         setResizable(false);// ���ɵ�����С
        setSize(width, height);// ���ÿ��
        Toolkit tool = Toolkit.getDefaultToolkit(); // ����ϵͳ��Ĭ��������߰�
        Dimension d = tool.getScreenSize(); // ��ȡ��Ļ�ߴ磬����һ����ά�������
        // ������������Ļ�м���ʾ
        setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// ����رմ��岻���κβ���
        addListener();// ��Ӽ���
        setPanel(new StarPanel(this));// ���뿪ʼ���
        GameMapUtil.clearCustomMap();// ɾ���Զ����ͼ
    }
    /**
     * �����������е����
     * 
     * @param panel
     *            - ���������
     */
    
    public void setPanel(JPanel panel) {
        Container c = getContentPane();// ��ȡ����������
        c.removeAll();// ɾ���������������
        c.add(panel, BorderLayout.CENTER);// ����������
        c.validate();// ����������֤�������
        c.repaint();
    }

    /**
     * ����������
     */
    private void addListener() {
        addWindowListener(new WindowAdapter() {// ��Ӵ����¼�����
            public void windowClosing(WindowEvent e) {// ����ر�ʱ
                int closeCode = JOptionPane.showConfirmDialog(MainFrame.this,
                        "�Ƿ��˳���Ϸ��", "��ʾ��", JOptionPane.YES_NO_OPTION);// ����ѡ��Ի��򣬲���¼�û�ѡ��
                if (closeCode == JOptionPane.YES_OPTION) {// ����û�ѡ��ȷ��
                    System.exit(0);// �رճ���
                }
            }
        });
    }

}
