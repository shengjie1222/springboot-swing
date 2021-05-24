package com.ethereal.swing.menu;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 * @Description
 * @create 2021-05-19 16:09
 */
public class SwingArea extends JFrame {

    private static SwingArea instance = null;
    private JProgressBar progressBar;
    private SwingArea() {
    }
    public static SwingArea getInstance() {
        if (null == instance) {
            synchronized (SwingArea.class) {
                if (null == instance) {
                    instance = new SwingArea();
                }
            }
        }
        return instance;
    }
    public void initUI() {
        this.setTitle("商用车CODE数据处理程序");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 540);
        instance.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.setContentPane(panel);
        //文本区域
        JLabel fileFiled = new JLabel("无");

        AtomicReference<JFileChooser> file = new AtomicReference<>(new JFileChooser());

        JButton openBtn = new JButton("选择文件");
        openBtn.addActionListener(e -> file.set(showFileOpenDialog(this, fileFiled)));
        openBtn.setBounds(160,100,100,30);
        openBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        openBtn.setFont(new Font("宋体", Font.BOLD,15));
        openBtn.setForeground(Color.white);
        panel.add(openBtn);

        JButton action = new JButton("执行计算");
        action.setBounds(370,100,100,30);
        action.addActionListener(e -> action(file.get()));
        action.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        action.setFont(new Font("宋体", Font.BOLD,15));
        action.setForeground(Color.white);
        panel.add(action);
        panel.add(fileFiled);

        JLabel fileFiledTitle = new JLabel("已选文件：");
        fileFiledTitle.setBounds(130, 150, 150, 30);
        panel.add(fileFiledTitle);
        fileFiled.setBounds(200,150,500,30);
        progressBar = new JProgressBar();
        progressBar.setBounds(80,300,500,30);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        panel.add(progressBar);
        this.setVisible(true);

    }

    /**
     * 进度条模拟程序
     * @param progressBar
     */
    private void progressBar(JProgressBar progressBar) {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.setValue(i);
            }
        }).start();
    }

    private void action(JFileChooser fileChooser) {
        if (null == fileChooser || null == fileChooser.getSelectedFile()) {
            JOptionPane.showMessageDialog(null, "请先选择要处理的文件！╮(╯▽╰)╭", "警告！",JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("执行" + fileChooser.getSelectedFile().getAbsolutePath());
        progressBar(progressBar);

    }

    /**
     * 打开文件
     */
    private JFileChooser showFileOpenDialog(Component parent, JLabel textField) {

        if (progressBar.getValue() != 0 && progressBar.getValue() != 100) {
            JOptionPane.showMessageDialog(null, "计算过程中，不可更改文件！╮(╯▽╰)╭", "警告！", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setMultiSelectionEnabled(false);
        // 设置默认使用的文件过滤器
        jFileChooser.setFileFilter(new FileNameExtensionFilter("excel(*.xlsx, *.xls)", "xls", "xlsx"));
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = jFileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = jFileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
            textField.setText("");
            textField.setText(file.getName() + "\n\n");
        }
        //进度条归零
        progressBar.setValue(0);
        return jFileChooser;
    }
}