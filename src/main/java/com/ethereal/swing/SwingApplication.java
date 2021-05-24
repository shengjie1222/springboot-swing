package com.ethereal.swing;

import com.ethereal.swing.menu.SwingArea;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

/**
 * @author Administrator
 */
@SpringBootApplication
public class SwingApplication {
    public SwingApplication() {
        SwingArea.getInstance().initUI();
    }

    public static void main(String[] args) {

        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible", false);
        } catch(Exception e) {
            e.printStackTrace();
        }
        ApplicationContext ctx = new SpringApplicationBuilder(SwingApplication.class)
                .headless(false).run(args);
    }

}
