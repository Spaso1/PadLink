package com.astral.padlink.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@RestController
public class AppController {
    private static int old_x = 0;
    private static int old_y = 0;
    private static int may_old_x = 0;
    private static int may_old_y = 0;
    private static int method1 = 0;
    @GetMapping("/move")
    public static String get(double x1,double y1,int method,boolean startBoolean) throws AWTException {
        System.setProperty("java.awt.headless", "false");
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int actualX = (int) (screenWidth * x1);
        int actualY = (int) (screenHeight * y1);
        if(!(method1 ==method)) {
            method1 = method;
            old_x = actualX;
            old_y = actualY;
            return "1";
        }
        if(old_x==0 && old_y==0) {
            old_x = actualX;
            old_y = actualY;
            return "1";
        }
        if (startBoolean) {
            old_x = actualX;
            old_y = actualY;
        }
        //System.out.println("实际坐标: (" + actualX + ", " + actualY + ")");
        Robot robot = new Robot();
        if(method==2) {
            //获取目前鼠标位置
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;

            int tagert_x = squarePreserveSign((actualX-old_x))/5000 + x;
            int tagert_y = squarePreserveSign((actualY-old_y))/5000 + y;
            robot.mouseMove(tagert_x, tagert_y);
            if(may_old_x==actualX && may_old_y==actualY) {
                old_x = actualX;
                old_y = actualY;
            }
            may_old_x = actualX;
            may_old_y = actualY;
            //目标坐标
        }else if(method==3) {
            //获取目前鼠标位置
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;

            int tagert_x = (actualX-old_x)/20 + x;
            int tagert_y = (actualY-old_y)/20 + y;
            robot.mouseMove(tagert_x, tagert_y);
            if(may_old_x==actualX && may_old_y==actualY) {
                old_x = actualX;
                old_y = actualY;
            }
            may_old_x = actualX;
            may_old_y = actualY;
            //目标坐标
        }else {
            robot.mouseMove(actualX, actualY);
        }
        // 假设 x1 和 y1 是百分比值，例如 0.5 表示 50%
        return "Hello World!";
    }
    @GetMapping("/click")
    public static String click(double x1,double y1,int method) throws AWTException {
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
        if(method==0) {
            robot.mouseMove(actualX, actualY);
        }
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        // 假设 x1 和 y1 是百分比值，例如 0.5 表示 50%
        return "Hello World!";
    }
    @GetMapping("/right")
    public static String right(double x1,double y1,int method) throws AWTException {
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

        if(method==0) {
            robot.mouseMove(actualX, actualY);
        }        //右键
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        // 假设 x1 和 y1 是百分比值，例如 0.5 表示 50%
        return "Hello World!";
    }
    @GetMapping("/windowRight")
    public static String windowRight(int method) throws AWTException {
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
    public static String windowLeft(int method) throws AWTException {
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
    public static String dragY(double y,int method) throws AWTException {
        System.setProperty("java.awt.headless", "false");
        System.out.println("y:" + y);

        // 根据 y 的值计算滚动速度
        int scrollAmount = (int) (y * y * 4); // 将 y 映射为滚动速度（1 到 10）
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
    @GetMapping("/tabwin")
    public static String tabwin(int method) throws AWTException {
        System.setProperty("java.awt.headless", "false");

        Robot robot = new Robot();
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // Windows 系统：Win + Tab
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
        } else if (os.contains("mac")) {
            // macOS 系统：Control + 上箭头
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } else {
            // 其他系统（如 Linux）的处理逻辑
            throw new UnsupportedOperationException("Unsupported operating system");
        }
        return "tabwin";
    }

    public static int squarePreserveSign(int number) {
        // 保存原始数的符号
        int sign = (int) Math.signum(number);
        // 计算平方
        int squared = number * number;
        // 将原始符号应用到平方结果上
        return sign * squared;
    }
}