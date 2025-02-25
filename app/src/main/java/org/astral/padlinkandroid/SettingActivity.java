package org.astral.padlinkandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import org.astral.padlinkandroid.been.SettingItem;
import org.astral.padlinkandroid.been.SettingsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity implements SettingsAdapter.OnItemClickListener {
    private RecyclerView settingsRecyclerView;
    private SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 初始化 RecyclerView
        settingsRecyclerView = findViewById(R.id.settingsRecyclerView);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        // 设置适配器
        SettingsAdapter adapter = new SettingsAdapter(getSettingsItems());
        adapter.setOnItemClickListener(this); // 设置点击监听器
        settingsRecyclerView.setAdapter(adapter);
    }

    // 获取设置项数据
    private List<SettingItem> getSettingsItems() {
        List<SettingItem> items = new ArrayList<>();
        items.add(new SettingItem("主机", SettingItem.TYPE_HOST));
        items.add(new SettingItem("连接方式", SettingItem.TYPE_SELECT));
        items.add(new SettingItem("操作方法", SettingItem.TYPE_METHOD));
        items.add(new SettingItem("隐私", SettingItem.TYPE_YINSI));
        items.add(new SettingItem("回到主页面", SettingItem.TYPE_BACK));
        items.add(new SettingItem("关于", SettingItem.TYPE_INFO));
        return items;
    }

    // 处理点击事件
    @Override
    public void onItemClick(int position, SettingItem item) {
        switch (item.getType()) {
            case SettingItem.TYPE_HOST:
                handleHostSetting();
                break;
            case SettingItem.TYPE_SELECT:
                handleLinkSetting();
                break;
            case SettingItem.TYPE_INFO:
                handleAboutSetting();
                break;
            case SettingItem.TYPE_METHOD:
                handleMethodSetting();
                break;
            case SettingItem.TYPE_YINSI:
                handleAboutSetting();
                break;
                case SettingItem.TYPE_BACK:
                back();
                break;
            default:
                break;
        }
    }

    private void back() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 清除堆栈中的实例
        startActivity(intent);
        finish(); // 结束当前 Activity
    }

    private void handleMethodSetting() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog_Alert);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_methodlink_setting, null);
        builder.setView(dialogView);

        // 获取布局中的控件
        RadioGroup connectionTypeGroup = dialogView.findViewById(R.id.connectionTypeGroup);
        //设置选中项
        int s = sharedPreferences.getInt("method", 0);
        switch (s) {
            case 0:
                connectionTypeGroup.check(R.id.aRadioButton);
                break;
            case 1:
                connectionTypeGroup.check(R.id.bRadioButton);
                break;
            case 2:
                connectionTypeGroup.check(R.id.cRadioButton);
                break;
            case 3:
                connectionTypeGroup.check(R.id.dRadioButton);
                break;
            case 4:
                connectionTypeGroup.check(R.id.eRadioButton);
                break;
            default:
                break;
        }
        MaterialButton confirmButton = dialogView.findViewById(R.id.confirmButton);
        TextView a = dialogView.findViewById(R.id.a);
        TextView b = dialogView.findViewById(R.id.b);
        TextView c = dialogView.findViewById(R.id.c);
        TextView d = dialogView.findViewById(R.id.d);
        TextView e = dialogView.findViewById(R.id.e);
        int checked = connectionTypeGroup.getCheckedRadioButtonId();
        if (checked == R.id.aRadioButton) {
            a.setVisibility(View.VISIBLE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
        }else if (checked == R.id.bRadioButton) {
            a.setVisibility(View.GONE);
            b.setVisibility(View.VISIBLE);
            c.setVisibility(View.GONE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
        }else if (checked == R.id.cRadioButton) {
            a.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.VISIBLE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
        }else if (checked == R.id.dRadioButton) {
            a.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            d.setVisibility(View.VISIBLE);
            e.setVisibility(View.GONE);
        }else if (checked == R.id.eRadioButton) {
            a.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
            c.setVisibility(View.GONE);
            d.setVisibility(View.GONE);
            e.setVisibility(View.VISIBLE);
        }

        connectionTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.aRadioButton) {
                a.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
            }else if (checkedId == R.id.bRadioButton) {
                a.setVisibility(View.GONE);
                b.setVisibility(View.VISIBLE);
                c.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
            }else if (checkedId == R.id.cRadioButton) {
                a.setVisibility(View.GONE);
                b.setVisibility(View.GONE);
                c.setVisibility(View.VISIBLE);
                d.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
            }else if (checkedId == R.id.dRadioButton) {
                a.setVisibility(View.GONE);
                b.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                d.setVisibility(View.VISIBLE);
                e.setVisibility(View.GONE);
            }else if (checkedId == R.id.eRadioButton) {
                a.setVisibility(View.GONE);
                b.setVisibility(View.GONE);
                c.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                e.setVisibility(View.VISIBLE);
            }
        });

            // 创建 Dialog
        AlertDialog dialog = builder.create();
        MaterialButton cancelButton = dialogView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> dialog.dismiss());
        confirmButton.setOnClickListener(v -> {
            int selectedId = connectionTypeGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.aRadioButton) {
                sharedPreferences.edit().putInt("method", 0).apply();
                Toast.makeText(this,"操作模式0 重启程序或使用设置进入主程序可用",Toast.LENGTH_SHORT).show();
            } else if (selectedId == R.id.bRadioButton) {
                sharedPreferences.edit().putInt("method", 1).apply();
                Toast.makeText(this,"操作模式1 重启程序或使用设置进入主程序可用",Toast.LENGTH_SHORT).show();
            }else if (selectedId == R.id.cRadioButton) {
                sharedPreferences.edit().putInt("method", 2).apply();
                Toast.makeText(this,"操作模式2 重启程序或使用设置进入主程序可用",Toast.LENGTH_SHORT).show();
            }else if (selectedId == R.id.dRadioButton) {
                sharedPreferences.edit().putInt("method", 3).apply();
                Toast.makeText(this,"操作模式3 重启程序或使用设置进入主程序可用",Toast.LENGTH_SHORT).show();
            }
            else if (selectedId == R.id.eRadioButton) {
                sharedPreferences.edit().putInt("method", 4).apply();
                Toast.makeText(this,"操作模式4 重启程序或使用设置进入主程序可用",Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    @SuppressLint("MissingInflatedId")
    // 处理通知设置
    private void handleHostSetting() {
        // 创建 Dialog
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog_Alert);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_host_setting, null);
        builder.setView(dialogView);

        // 获取布局中的控件
        TextInputEditText hostInput = dialogView.findViewById(R.id.hostInput);
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);

        if (sharedPreferences.getBoolean("udp", false)) {
            dialogTitle.setText("输入主机地址 注意!目前使用udp模式,此地址已无用");
        }
        hostInput.setText(sharedPreferences.getString("host",""));
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        // 创建 Dialog
        AlertDialog dialog = builder.create();

        // 取消按钮点击事件
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        // 确认按钮点击事件
        confirmButton.setOnClickListener(v -> {
            String host = hostInput.getText().toString().trim();
            if (!host.isEmpty()) {
                // 处理主机地址输入
                Toast.makeText(this, "网络地址: " + host, Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putString("host", host).apply();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "请输入有效的主机地址", Toast.LENGTH_SHORT).show();
            }
        });

        // 显示 Dialog
        dialog.show();
    }
    @SuppressLint("MissingInflatedId")
    // 处理主题设置
    private void handleLinkSetting() {
        // 创建 Dialog
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog_Alert);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_method_setting, null);
        builder.setView(dialogView);

        // 获取布局中的控件
        RadioGroup connectionTypeGroup = dialogView.findViewById(R.id.connectionTypeGroup);
        LinearLayout udpInputLayout = dialogView.findViewById(R.id.udpInputLayout);
        TextInputEditText udpHostInput = dialogView.findViewById(R.id.udpHostInput);
        TextInputEditText udpPortInput = dialogView.findViewById(R.id.udpPortInput);
        MaterialButton cancelButton = dialogView.findViewById(R.id.cancelButton);
        MaterialButton confirmButton = dialogView.findViewById(R.id.confirmButton);
        //设置默认选中
        connectionTypeGroup.check(R.id.tcpRadioButton);
        if (sharedPreferences.getBoolean("udp", false)) {
            connectionTypeGroup.check(R.id.udpRadioButton);
            udpInputLayout.setVisibility(View.VISIBLE);
            udpHostInput.setText(sharedPreferences.getString("udphost", ""));
            udpPortInput.setText("" + sharedPreferences.getInt("udpport", 1000));
        }
        // 设置 RadioGroup 的监听器
        connectionTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.udpRadioButton) {
                udpInputLayout.setVisibility(View.VISIBLE);
            } else {
                udpInputLayout.setVisibility(View.GONE);
            }
        });

        // 创建 Dialog
        AlertDialog dialog = builder.create();

        // 取消按钮点击事件
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        // 确认按钮点击事件
        confirmButton.setOnClickListener(v -> {
            int selectedId = connectionTypeGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.tcpRadioButton) {
                // 处理 TCP 连接
                Toast.makeText(this, "TCP 连接已选择", Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putBoolean("udp", false).apply();
            } else if (selectedId == R.id.udpRadioButton) {
                // 处理 UDP 连接
                String udpHost = udpHostInput.getText().toString().trim();
                String udpPort = udpPortInput.getText().toString().trim();
                if (!udpHost.isEmpty() && !udpPort.isEmpty()) {
                    Toast.makeText(this, "UDP 主机: " + udpHost + ", 端口: " + udpPort, Toast.LENGTH_SHORT).show();
                    try {
                        sharedPreferences.edit().putBoolean("udp", true).putString("udphost", udpHost).putInt("udpport", Integer.parseInt(udpPort)).apply();
                    }catch (NumberFormatException e) {
                        Toast.makeText(this, "请输入有效的 UDP 端口", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入有效的 UDP 主机和端口", Toast.LENGTH_SHORT).show();
                }
            }
            dialog.dismiss();
        });

        // 显示 Dialog
        dialog.show();
    }


    // 处理关于设置
    private void handleAboutSetting() {
        // 调用关于设置相关逻辑
        //Toast.makeText(this, "关于设置", Toast.LENGTH_SHORT).show();
    }
}
