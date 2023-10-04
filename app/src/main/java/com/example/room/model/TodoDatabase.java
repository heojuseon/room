package com.example.room.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


//

//@Database 의 entites = : entity 정의
//@Database 의 version = : SQLite 버전 지정
@Database(entities = {TodoModel.class}, version = 1)    //Room 데이터베이스 정의
public abstract class TodoDatabase extends RoomDatabase {   //실질적인 데이터베이스 인스턴스를 생성하는 TodoDatabase 클래스는 RoomDatabase 를 상속하는 추상 클래스로 생성

    public abstract TodoDAO todoDao();

    private static TodoDatabase INSTANCE;   //데이터베이스 인스턴스를 싱글톤으로 관리, 데이터베이스는 애플리케이션 전체에서 한 번만 생성되어야 한다.

    public static TodoDatabase getInstance(Context context) {   // 데이터베이스 인스턴스를 가져오기 위한 정적 메서드

        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) { //여러 스레드가 접근하지 못하도록 synchronized로 설정

                INSTANCE = Room.databaseBuilder(    //데이터베이스를 (인스턴스)생성
                                context.getApplicationContext(),
                                TodoDatabase.class,
                                "tb_todo"
                        )
                        .fallbackToDestructiveMigration()   //데이터베이스 버전 업그레이드를 처리하기 위한 방법 중 하나, 데이터베이스가 갱신될 때 기존의 테이블을 버리고 새로 사용하도록 설정
                        .build();
            }
        }
        return INSTANCE;
    }
}
