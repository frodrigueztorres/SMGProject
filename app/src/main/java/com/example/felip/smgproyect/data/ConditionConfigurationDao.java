package com.example.felip.smgproyect.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.felip.smgproyect.data.model.ConditionConfiguration;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface ConditionConfigurationDao {
    @Query("Select * from condition_configuration")
    Maybe<List<ConditionConfiguration>> getAll();

    @Query("Select * from condition_configuration where condition = :condition")
    Maybe<ConditionConfiguration> getByCondition(ConditionConfiguration.Condition condition);

    @Query("Update condition_configuration set low = :low, medium = :medium, high = :high where condition = :condition")
    void update(int low, int medium, int high, ConditionConfiguration.Condition condition);

    @Query("Delete from condition_configuration")
    void deleteAll();

    @Insert
    List<Long> insertAll(List<ConditionConfiguration> conditionConfigurationList);
}
