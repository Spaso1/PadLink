package org.astral.padlinkandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MultiTouchActivity";
    private TouchPointView touchPointView; // 自定义View

    private float startX, startY; // 记录按下时的初始位置
    private boolean isDragging = false; // 是否正在拖动
    private static final float DRAG_THRESHOLD = 10; // 拖动的最小距离阈值
    private OkHttpClient client;
    private RelativeLayout back;
    private boolean isNoTouch = true;
    private long lastMoveTime = 0; // 记录上一次调用 sendMove 的时间
    private static final long MOVE_INTERVAL = 20; // 时间间隔（单位：毫秒）
    private Map<Integer, float[]> pointerIds;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        back = findViewById(R.id.back);
        pointerIds = new HashMap<>();
        // 初始化自定义View
        touchPointView = findViewById(R.id.touch_point_view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取触摸点的数量
        int pointerCount = event.getPointerCount();
        // 遍历所有触摸点
        for (int i = 0; i < pointerCount; i++) {
            // 获取触摸点的ID
            int pointerId = event.getPointerId(i);

            // 获取触摸点的绝对坐标
            float x = event.getX(i);
            float y = event.getY(i);
            pointerIds.put(pointerId, new float[]{x, y});

            // 更新触摸点位置
            touchPointView.setTouchPoint(pointerId, x, y);

            // 打印触摸点信息
            Log.d(TAG, "Pointer ID: " + pointerId +
                    ", X: " + x + ", Y: " + y);
        }

        // 根据触摸事件类型进行处理
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                // 如果没有触摸后再次触摸，清除历史记录
                if (isNoTouch) {
                    touchPointView.clearHistoryPoints();
                    isNoTouch = false;
                }
                // 记录按下时的初始位置
                startX = event.getX();
                startY = event.getY();
                isDragging = false;
                Log.d(TAG, "Pointer down");
                break;

            case MotionEvent.ACTION_MOVE:
                // 计算移动距离
                float deltaX = event.getX() - startX;
                float deltaY = event.getY() - startY;
                float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                // 判断是否拖动
                if (distance > DRAG_THRESHOLD) {
                    isDragging = true;

                    // 获取当前时间
                    long currentTime = System.currentTimeMillis();

                    // 判断是否超过时间间隔
                    if (currentTime - lastMoveTime >= MOVE_INTERVAL) {
                        // 计算百分比
                        double xPercent = event.getX() / back.getWidth();
                        double yPercent = event.getY() / back.getHeight();

                        // 在子线程中执行网络请求
                        new Thread(() -> {
                            try {
                                sendMove(xPercent, yPercent);
                            } catch (IOException e) {
                                Log.e(TAG, "Network request failed", e);
                            }
                        }).start();

                        // 更新上一次调用 sendMove 的时间
                        lastMoveTime = currentTime;
                    }

                    Log.d(TAG, "Dragging");
                }
                break;

            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Pointer up" + pointerIds.size());
                if (pointerIds.size() == 2) {
                    float finger1X = Objects.requireNonNull(pointerIds.get(0))[0];
                    float finger1Y = Objects.requireNonNull(pointerIds.get(0))[1];
                    float finger2X = Objects.requireNonNull(pointerIds.get(1))[0];
                    float finger2Y = Objects.requireNonNull(pointerIds.get(1))[1];

                    // 计算两个手指之间的距离
                    float distance2 = (float) Math.sqrt(
                            Math.pow(finger1X - finger2X, 2) +
                                    Math.pow(finger1Y - finger2Y, 2)
                    );
                    Log.d(TAG, "Distance: " + distance2);
                    // 如果距离小于阈值（例如 50 像素），则认为是两个手指靠近点击
                    if (distance2 < 300) {
                        // 计算中心点坐标
                        double xPercent = (Objects.requireNonNull(pointerIds.get(0))[0] + Objects.requireNonNull(pointerIds.get(1))[0]) / 2 / back.getWidth();
                        double yPercent = (Objects.requireNonNull(pointerIds.get(1))[1] + Objects.requireNonNull(pointerIds.get(1))[1]) / 2 / back.getHeight();

                        // 在子线程中执行 sendRight
                        new Thread(() -> {
                            try {
                                sendRight(xPercent, yPercent);
                                Log.d(TAG, "Right Clicked");
                            } catch (IOException e) {
                                Log.e(TAG, "Network request failed", e);
                            }
                        }).start();
                    }
                    if(isNoTouch) {
                        touchPointView.clearHistoryPoints();
                        isNoTouch = false;
                        pointerIds = new HashMap<>();
                    }
                    int pointerId = event.getPointerId(event.getActionIndex());
                    touchPointView.clearTouchPoint(pointerId);

                    // 如果没有触摸点，标记为没有触摸
                    if (event.getPointerCount() == 1) {
                        isNoTouch = true;
                    }
                    break;
                }
                if (!isDragging) {

                    // 如果未拖动，则为点击
                    // 获取当前时间
                    long currentTime = System.currentTimeMillis();

                    // 判断是否超过时间间隔
                    if (currentTime - lastMoveTime >= MOVE_INTERVAL) {
                        // 计算百分比
                        double xPercent = event.getX() / back.getWidth();
                        double yPercent = event.getY() / back.getHeight();

                        // 在子线程中执行网络请求
                        new Thread(() -> {
                            try {
                                sendClick(xPercent, yPercent);
                                Log.d(TAG, "Clicked");
                            } catch (IOException e) {
                                Log.e(TAG, "Network request failed", e);
                            }
                        }).start();

                        // 更新上一次调用 sendMove 的时间
                        lastMoveTime = currentTime;
                    }
                } else {
                    // 如果拖动，则为拖动结束
                    Log.d(TAG, "Drag ended");
                }
                // 清除当前触摸点
                int pointerId = event.getPointerId(event.getActionIndex());
                touchPointView.clearTouchPoint(pointerId);

                // 如果没有触摸点，标记为没有触摸
                if (event.getPointerCount() == 1) {
                    isNoTouch = true;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                // 清除所有触摸点
                touchPointView.clearAllTouchPoints();
                isNoTouch = true; // 标记为没有触摸
                break;
        }
        if(isNoTouch) {
            pointerIds = new HashMap<>();
        }
        // 返回true表示事件已处理，false表示继续传递事件
        return true;
    }


    public void sendMove(double x, double y) throws IOException {
        Request request = new Request.Builder()
                .url("http://192.168.230.177:8081/move?x1=" + x + "&y1=" + y)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void sendClick(double x, double y) throws IOException {
        Request request = new Request.Builder()
                .url("http://192.168.230.177:8081/click?x1=" + x + "&y1=" + y)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }
    public void sendRight(double x, double y) throws IOException {
        Request request = new Request.Builder()
                .url("http://192.168.230.177:8081/right?x1=" + x + "&y1=" + y)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }
}
