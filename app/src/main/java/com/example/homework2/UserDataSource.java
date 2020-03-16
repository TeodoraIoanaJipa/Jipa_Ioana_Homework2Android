package com.example.homework2;

import java.util.List;

import io.reactivex.Flowable;

public class UserDataSource implements iUserDataSource {
    private UserDao userDAO;
    private static UserDataSource mInstance;

    private UserDataSource(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    public static UserDataSource getInstance(UserDao userDAO){
        if(mInstance == null){
            mInstance = new UserDataSource(userDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User findByName(String first) {
        return userDAO.findByName(first);
    }

    @Override
    public void insertAll(User... users) {
        userDAO.insertAll(users);
    }

    @Override
    public void updateUser(User... users) {
        userDAO.updateUser(users);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();
    }
}
