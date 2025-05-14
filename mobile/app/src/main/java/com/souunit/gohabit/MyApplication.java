package com.souunit.gohabit;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("FIREBASE_INIT", "Firebase Apps: " + FirebaseApp.getApps(this).size());
    }
}