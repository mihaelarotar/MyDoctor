package com.example.mydoctor2.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert()
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user")
    List<User> observeAllUser();
}
