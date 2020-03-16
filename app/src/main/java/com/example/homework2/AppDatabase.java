package com.example.homework2;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = Constants.DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    /*varianta tutorial*/
    private static AppDatabase mInstance;
    public static AppDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context, AppDatabase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }
}

