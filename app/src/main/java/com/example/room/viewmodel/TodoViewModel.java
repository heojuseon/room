package com.example.room.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.room.model.TodoModel;
import com.example.room.repository.TodoRepository;

import java.util.List;

//ViewModel : View 를 위한 데이터를 가지고 있다.
//데이터를 가져오는 TodoViewModel 클래스를 AndroidViewModel 를 상속하는 클래스로 생성( ViewModel 의 Context 를 사용하면 액티비티가 destroy 되는 경우 메모리 에러가 발생할 수 있어서 Application application 사용)
public class TodoViewModel extends AndroidViewModel {
    private TodoRepository todoRepository;
    private LiveData<List<TodoModel>> todoItems;

    public TodoViewModel(@NonNull Application application) {    //생성자 초기화 작업
        super(application);
        todoRepository = new TodoRepository(application);   // 애플리케이션 컨텍스트를 사용하여 TodoRepository를 초기화하고 데이터베이스 작업을 수행
        todoItems = todoRepository.getTodoListAll();
    }

    public LiveData<List<TodoModel>> getTodoListAll() {
        return todoItems;
    }

    public void insert(TodoModel todoModel) {
        todoRepository.insert(todoModel);
    }

    public void delete(TodoModel todoModel) {
        todoRepository.delete(todoModel);
    }
}
