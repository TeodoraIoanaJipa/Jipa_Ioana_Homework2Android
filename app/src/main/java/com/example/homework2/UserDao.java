package com.example.homework2;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao{

    @Query("SELECT * FROM users")
    Flowable<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :userId")
    Flowable<User> getUserById(int userId);

    @Query("SELECT * FROM users WHERE first_and_last_name LIKE :first LIMIT 1")
    User findByName(String first);

    @Insert
    void insertAll(User... users);

    @Update
    void updateUser(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
