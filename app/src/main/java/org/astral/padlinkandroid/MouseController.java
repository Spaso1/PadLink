package org.astral.padlinkandroid;

import android.content.Context;
import android.hardware.usb.UsbDevice;

public class MouseController {
    private UsbCommunication usbCommunication;

    public MouseController(Context context, UsbDevice device) {
        usbCommunication = new UsbCommunication(context);
        usbCommunication.openDevice(device);
    }

    public void move(double x1, double y1) {
        usbCommunication.sendData("MOVE", x1, y1);
    }

    public void click(double x1, double y1) {
        usbCommunication.sendData("CLICK", x1, y1);
    }

    public void right(double x1, double y1) {
        usbCommunication.sendData("RIGHT", x1, y1);
    }

    public void close() {
        usbCommunication.close();
    }
}
