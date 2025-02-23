package com.astral.padlink;

import com.astral.padlink.service.UdpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PadLinkApplication {
    @Autowired
    private static UdpServer udpServer;

    public static void main(String[] args) {
        udpServer = new UdpServer();
        new Thread(()->{
            udpServer.start();
        }).start();
        SpringApplication.run(PadLinkApplication.class, args);
    }
}
