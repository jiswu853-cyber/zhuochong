package com.example.desktoppet.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.desktoppet.R;
import com.example.desktoppet.widget.PetView;

public class FloatingPetService extends Service {

    private WindowManager windowManager;
    private FrameLayout floatingView;
    private PetView petView;
    private TextView dialogBox;
    private WindowManager.LayoutParams params;
    private float dX, dY;
    private int lastAction;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        createFloatingPet();
    }

    private void createFloatingPet() {
        // 创建浮窗容器
        floatingView = new FrameLayout(this);
        floatingView.setLayoutParams(new FrameLayout.LayoutParams(
                150, 150, Gravity.CENTER));

        // 创建宠物视图
        petView = new PetView(this);
        floatingView.addView(petView, new FrameLayout.LayoutParams(
                150, 150, Gravity.CENTER));

        // 创建对话框
        dialogBox = new TextView(this);
        dialogBox.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        dialogBox.setPadding(16, 8, 16, 8);
        dialogBox.setTextSize(12);
        dialogBox.setVisibility(View.GONE);
        floatingView.addView(dialogBox, new FrameLayout.LayoutParams(
                150, 80, Gravity.TOP | Gravity.CENTER_HORIZONTAL));

        // 设置触摸监听 - 拖拽和点击
        floatingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = event.getRawX() - params.x;
                        dY = event.getRawY() - params.y;
                        lastAction = MotionEvent.ACTION_DOWN;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        params.x = (int) (event.getRawX() - dX);
                        params.y = (int) (event.getRawY() - dY);
                        windowManager.updateViewLayout(floatingView, params);
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            // 点击事件 - 显示对话框
                            showDialogBox();
                        }
                        return true;
                }
                return false;
            }
        });

        // 设置窗口参数
        params = new WindowManager.LayoutParams();
        params.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = 150;
        params.height = 150;
        params.x = 50;
        params.y = 50;

        windowManager.addView(floatingView, params);
    }

    private void showDialogBox() {
        // 显示对话输入框
        dialogBox.setText("输入你的话...");
        dialogBox.setVisibility(View.VISIBLE);
    }

    public void showPetReply(String message) {
        dialogBox.setText(message);
        dialogBox.setVisibility(View.VISIBLE);
        
        // 3秒后自动隐藏
        floatingView.postDelayed(() -> {
            dialogBox.setVisibility(View.GONE);
        }, 3000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) {
            windowManager.removeView(floatingView);
        }
    }
}