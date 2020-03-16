package com.example.homework2;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

public interface iUserDataSource {

    Flowable<User> getUserById(int userId);
    Flowable<List<User>> getAllUsers();
    User findByName(String first);
    void insertAll(User... users);
    void updateUser(User... users);
    void delete(User user);
    void deleteAllUsers();
}
