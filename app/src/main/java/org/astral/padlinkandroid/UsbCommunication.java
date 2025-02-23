package org.astral.padlinkandroid;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UsbCommunication {
    private static final String TAG = "UsbCommunication";
    private UsbManager usbManager;
    private UsbDeviceConnection connection;
    private UsbEndpoint endpoint;

    public UsbCommunication(Context context) {
        usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
    }

    public boolean openDevice(UsbDevice device) {
        UsbInterface usbInterface = device.getInterface(0);
        connection = usbManager.openDevice(device);
        if (connection == null) {
            Log.e(TAG, "Failed to open USB device");
            return false;
        }
        connection.claimInterface(usbInterface, true);
        endpoint = usbInterface.getEndpoint(0); // 选择合适的端点
        return true;
    }

    public void sendData(String command, double x1, double y1) {
        if (connection == null || endpoint == null) {
            Log.e(TAG, "USB device not connected");
            return;
        }

        // 将命令和坐标数据打包
        ByteBuffer buffer = ByteBuffer.allocate(20); // 20字节的缓冲区
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(command.getBytes());
        buffer.putDouble(x1);
        buffer.putDouble(y1);

        // 发送数据
        int bytesSent = connection.bulkTransfer(endpoint, buffer.array(), buffer.position(), 1000);
        Log.d(TAG, "Sent " + bytesSent + " bytes: " + command + " (" + x1 + ", " + y1 + ")");
    }

    public void close() {
        if (connection != null) {
            connection.close();
        }
    }
}
