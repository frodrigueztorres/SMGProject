package com.example.felip.smgproyect.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.felip.smgproyect.data.model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface UserDao {
    @Query("Select * from user")
    Maybe<List<User>> getAll();

    @Query("select * from  user where username = :username and password = :password")
    Maybe<User> getUserByUsernameAndPassword(String username, String password);

    @Insert
    long insert(User user);
}
