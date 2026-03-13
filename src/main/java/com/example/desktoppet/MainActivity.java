package com.example.desktoppet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class FloatingPetService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize your floating pet
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Code for floating pet behavior
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Clean up resources
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // We don't provide binding
    }
}