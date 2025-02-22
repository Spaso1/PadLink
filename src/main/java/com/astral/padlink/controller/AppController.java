package com.astral.padlink.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@RestController
public class AppController {
    @GetMapping("/move")
    public String get(double x1,double y1) throws AWTException {
        System.setProperty("java.awt.headless", "false");
        System.out.println("x1:"+x1+"y1:"+y1);
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int actualX = (int) (screenWidth * x1);
        int actualY = (int) (screenHeight * y1);

        System.out.println("实际坐标: (" + actualX + ", " + actualY + ")");
        Robot robot = new Robot();
        robot.mouseMove(actualX, actualY);
        // 假设 x1 和 y1 是百分比值，例如 0.5 表示 50%
        return "Hello World!";
    }
    @GetMapping("/click")
    public String click(double x1,double y1) throws AWTException {
        System.setProperty("java.awt.headless", "false");
        System.out.println("x1:"+x1+"y1:"+y1);
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int actualX = (int) (screenWidth * x1);
        int actualY = (int) (screenHeight * y1);

        System.out.println("实际坐标: (" + actualX + ", " + actualY + ")");
        Robot robot = new Robot();
        robot.mouseMove(actualX, actualY);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        // 假设 x1 和 y1 是百分比值，例如 0.5 表示 50%
        return "Hello World!";
    }
    @GetMapping("/right")
    public String right(double x1,double y1) throws AWTException {
        System.setProperty("java.awt.headless", "false");
        System.out.println("x1:"+x1+"y1:"+y1);
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int actualX = (int) (screenWidth * x1);
        int actualY = (int) (screenHeight * y1);

        System.out.println("实际坐标: (" + actualX + ", " + actualY + ")");
        Robot robot = new Robot();
        robot.mouseMove(actualX, actualY);
        //右键
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        // 假设 x1 和 y1 是百分比值，例如 0.5 表示 50%
        return "Hello World!";
    }
    @GetMapping("/windowRight")
    public String windowRight() throws AWTException {
        System.setProperty("java.awt.headless", "false");
        Robot robot = new Robot();

        // 模拟按下 Alt + Tab
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);

        return "Switched to next window!";
    }
    @GetMapping("/windowLeft")
    public String windowLeft() throws AWTException {
        System.setProperty("java.awt.headless", "false");
        Robot robot = new Robot();

        // 模拟按下 Alt + Shift + Tab
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_ALT);

        return "Switched to previous window!";
    }

    @GetMapping("/dragY")
    public String dragY(double y) throws AWTException {
        System.setProperty("java.awt.headless", "false");
        System.out.println("y:" + y);

        // 根据 y 的值计算滚动速度
        int scrollAmount = (int) (y * 3); // 将 y 映射为滚动速度（1 到 10）
        if (scrollAmount < 1) {
            scrollAmount = 1; // 最小滚动速度为 1
        }

        Robot robot = new Robot();

        // 根据 y 的值判断滚动方向
        if (y > 0) {
            // 向下滚动
            robot.mouseWheel(scrollAmount);
        } else {
            // 向上滚动
            robot.mouseWheel(-scrollAmount);
        }

        return "Scrolled with speed: " + scrollAmount;
    }

}