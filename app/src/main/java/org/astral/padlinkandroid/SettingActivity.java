package org.astral.padlinkandroid;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
        items.add(new SettingItem("隐私", SettingItem.TYPE_YINSI));
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
                handleThemeSetting();
                break;
            case SettingItem.TYPE_INFO:
                handleAboutSetting();
                break;
            default:
                break;
        }
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
                Toast.makeText(this, "主机地址: " + host, Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putString("host", host).apply();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "请输入有效的主机地址", Toast.LENGTH_SHORT).show();
            }
        });

        // 显示 Dialog
        dialog.show();
    }

    // 处理主题设置
    private void handleThemeSetting() {
        // 调用主题设置相关逻辑
        Toast.makeText(this, "主题设置", Toast.LENGTH_SHORT).show();
    }

    // 处理关于设置
    private void handleAboutSetting() {
        // 调用关于设置相关逻辑
        Toast.makeText(this, "关于设置", Toast.LENGTH_SHORT).show();
    }
}
