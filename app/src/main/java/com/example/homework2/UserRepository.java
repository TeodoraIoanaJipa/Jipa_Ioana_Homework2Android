package com.example.homework2;


import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import io.reactivex.Flowable;

public class UserRepository implements iUserDataSource{
    private iUserDataSource mLocalDataSource;
    private static UserRepository mInstance;

    public UserRepository(iUserDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getmInstance(iUserDataSource mLocalDataSource) {
        if(mInstance == null){
            mInstance = new UserRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<User> getUserById(int userId) {
        return mLocalDataSource.getUserById(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public User findByName(String first) {
        return mLocalDataSource.findByName(first);
    }

    @Override
    public void insertAll(User... users) {
        mLocalDataSource.insertAll(users);
    }

    @Override
    public void updateUser(User... users) {
        mLocalDataSource.updateUser(users);
    }

    @Override
    public void delete(User user) {
        mLocalDataSource.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        mLocalDataSource.deleteAllUsers();
    }
    //renunt varianta asta
//    private AppDatabase appDatabase;
//
//    public UserRepository(Context context) {
//        appDatabase = ApplicationController.getAppDatabase();
//    }
//    public void insertTask(final User user,
//                           final OnUserRepositoryActionListener listener) {
//        new InsertTask(listener).execute(user);
//    }
//    public User getUserByName(String firstName, String lastName){
//        return appDatabase.userDao().findByName(firstName, lastName);
//    }
//
//    private class InsertTask extends AsyncTask<User, Void, Void> {
//        OnUserRepositoryActionListener listener;
//        InsertTask(OnUserRepositoryActionListener listener) {
//            this.listener = listener;
//        }
//        @Override
//        protected Void doInBackground(User... users) {
//            appDatabase.userDao().insertAll(users[0]);
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            listener.actionSuccess();
//        }
//    }
}
