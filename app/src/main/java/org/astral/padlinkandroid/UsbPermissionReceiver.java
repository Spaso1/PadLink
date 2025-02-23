package org.astral.padlinkandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

public class UsbPermissionReceiver extends BroadcastReceiver {
    private static final String ACTION_USB_PERMISSION = "org.astral.padlinkandroid.USB_PERMISSION";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_USB_PERMISSION.equals(action)) {
            synchronized (this) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    if (device != null) {
                        // 权限已授予，可以访问设备
                        Log.d("USB_DEVICE", "Permission granted for device: " + device.getDeviceName());
                    }
                } else {
                    Log.d("USB_DEVICE", "Permission denied for device: " + device.getDeviceName());
                }
            }
        }
    }
}