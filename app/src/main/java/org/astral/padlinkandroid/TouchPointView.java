package org.astral.padlinkandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TouchPointView extends View {
    private Map<Integer, float[]> touchPoints = new HashMap<>(); // 存储当前触摸点的位置
    private List<float[]> historyPoints = new ArrayList<>(); // 存储历史触摸点的位置
    private Paint paint; // 画笔

    public TouchPointView(Context context) {
        super(context);
        init();
    }

    public TouchPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        // 使用颜色 purple_200
        paint.setColor(Color.parseColor("#FF018786"));
        paint.setStrokeWidth(20); // 设置点的大小
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制历史触摸点
        for (float[] point : historyPoints) {
            if (point != null) {
                canvas.drawCircle(point[0], point[1], 10, paint);
            }
        }

        // 绘制当前触摸点
        for (Map.Entry<Integer, float[]> entry : touchPoints.entrySet()) {
            float[] point = entry.getValue();
            if (point != null) {
                canvas.drawCircle(point[0], point[1], 10, paint);
            }
        }
    }

    // 更新触摸点位置
    public void setTouchPoint(int pointerId, float x, float y) {
        touchPoints.put(pointerId, new float[]{x, y});
        historyPoints.add(new float[]{x, y}); // 将当前触摸点添加到历史记录中
        invalidate(); // 触发重绘
    }

    // 清除触摸点
    public void clearTouchPoint(int pointerId) {
        touchPoints.remove(pointerId);
        invalidate(); // 触发重绘
    }

    // 清除所有触摸点
    public void clearAllTouchPoints() {
        touchPoints.clear();
        invalidate(); // 触发重绘
    }

    // 清除历史触摸点
    public void clearHistoryPoints() {
        historyPoints.clear();
        invalidate(); // 触发重绘
    }
}
