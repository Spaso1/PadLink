package org.astral.padlinkandroid.been;

public class SettingItem {
    public static final int TYPE_HOST = 1;
    public static final int TYPE_SELECT = 2;
    public static final int TYPE_INFO = 3;
    public static final int TYPE_YINSI = 4;
    public static final int TYPE_METHOD = 5;
    public static final int TYPE_BACK = 6;
    private String title;
    private int type;

    public SettingItem(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }
}
