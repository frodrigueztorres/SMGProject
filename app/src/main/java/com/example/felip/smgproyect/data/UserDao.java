package com.example.felip.smgproyect.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.felip.smgproyect.data.model.User;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface UserDao {
    @Query("Select * from user")
    Flowable<User> getAll();

    @Query("select * from  user where username = :username and password = :password")
    Maybe<User> getUserByUsernameAndPassword(String username, String password);

    @Insert
    void insert(User user);
}
