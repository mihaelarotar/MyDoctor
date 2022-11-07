package com.example.mydoctor2.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert()
    void insertUser(User user);

    @Update
    void updateUser(User user);
}
