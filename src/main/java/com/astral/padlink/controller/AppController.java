package com.astral.padlink.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.event.InputEvent;

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
}