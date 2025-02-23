package com.astral.padlink.service;

import com.astral.padlink.controller.AppController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

@Service
public class UdpServer {

    private static final int PORT = 9876;

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[1024];
            System.out.println("UDP server started on port " + PORT);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received message: " + receivedMessage);

                // 解析并处理消息
                processMessage(receivedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processMessage(String message) {
        try {
            String[] parts = message.split(":");
            if (parts.length < 3) {
                System.out.println("Invalid message format: " + message);
                return;
            }

            String action = parts[0]; // 操作类型，如 move、click、right 等
            double param1 = Double.parseDouble(parts[1]); // 第一个参数
            double param2 = Double.parseDouble(parts[2]); // 第二个参数

            // 根据操作类型调用AppController中的方法
            switch (action) {
                case "move":
                    AppController.get(param1, param2);
                    break;
                case "click":
                    AppController.click(param1, param2);
                    break;
                case "right":
                    AppController.right(param1, param2);
                    break;
                case "dragY":
                    AppController.dragY(param1);
                    break;
                default:
                    System.out.println("Unknown action: " + action);
            }
        } catch (Exception e) {
            System.out.println("Error processing message: " + message);
            e.printStackTrace();
        }
    }
}
