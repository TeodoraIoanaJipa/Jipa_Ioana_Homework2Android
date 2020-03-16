package com.example.homework2;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class ApplicationController extends Application {
    //n-am inteles, renunt la Controller
    private static ApplicationController mInstance;
    // Each RoomDatabase instance is fairly expensive,
    // and you rarely need access to multiple instances within a single process.
    private static AppDatabase mAppDatabase;

    public static ApplicationController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // Get a database instance to work with
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.DATABASE_NAME).build();
    }

//    public static AppDatabase getAppDatabase() {
//        return mAppDatabase;
//    }
}

