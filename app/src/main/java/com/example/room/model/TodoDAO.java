package com.example.room.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//SQL 작성을 위한 interface 생성
//@Query, @Insert, @Update, @Delete 등의 어노테이션을 사용 가능
//그 중 @Insert, @Update 는 onConflict 속성을 지정 가능
@Dao
public interface TodoDAO {
    @Query("SELECT * FROM tb_todo ORDER BY seq ASC")
    LiveData<List<TodoModel>> getTodoListAll();  //getAll 함수에 LiveData 를 반환

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //@Insert 의 onConflict = : 중복된 데이터의 경우 어떻게 처리할 것인지에 대한 처리를 지정
    void insert(TodoModel todo);

    @Delete
    void delete(TodoModel todo);
}
