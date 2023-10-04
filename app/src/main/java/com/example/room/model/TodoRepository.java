package com.example.room.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

//Repository : ViewModel 과 상호작용하기 위해 정리된(Clean) 데이터 API 를 들고 있는 클래스

//TodoModel, TodoDao, TodoDatabase 를 각각 초기화하고 ViewModel 에서 DB 접근을 요청할 때 수행할 함수 생성
//VIewModel 이 직접 DB 나 서버에 접근하지 않고, Repository 에 접근하여 앱의 데이터를 관리하여 필요한 데이터를 가져온다.

//데이터베이스 혹은 네트워크 통신을 통하여 데이터를 얻는 기능을 분리
//ViewModel 에서는 이 Repository 를 통해 데이터를 얻는다
public class TodoRepository {
    //database, dao todoItems 를 각각 초기화
    private TodoDatabase todoDatabase;
    private TodoDAO todoDao;
    private LiveData<List<TodoModel>> todoItems;

    public TodoRepository(Application application) {
        todoDatabase = TodoDatabase.getInstance(application);
        todoDao = todoDatabase.todoDao();
        todoItems = todoDao.getTodoListAll();
    }

    public LiveData<List<TodoModel>> getTodoListAll() {
        return todoItems;
    }

    public void insert(TodoModel todoModel) {
        try {
            Thread thread = new Thread(new Runnable() { ////별도 스레드를 통해 Room 데이터에 접근해야한다. 연산 시간이 오래 걸리는 작업은 메인 쓰레드가 아닌 별도의 쓰레드에서 하도록 되어있다. 그렇지 않으면 런타임에러 발생.
                @Override
                public void run() {
                    todoDao.insert(todoModel);
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(TodoModel todoModel) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    todoDao.delete(todoModel);
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
