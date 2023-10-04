package com.example.room.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.room.model.TodoModel;
import com.example.room.model.TodoRepository;

import java.util.List;

//ViewModel : View 를 위한 데이터를 가지고 있다.
//데이터를 가져오는 TodoViewModel 클래스를 AndroidViewModel 를 상속하는 클래스로 생성
public class TodoViewModel extends AndroidViewModel {
    private TodoRepository todoRepository;
    private LiveData<List<TodoModel>> todoItems;
    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        todoItems = todoRepository.getTodoListAll();    ///액티비티(View) 에서 ViewModel 의 todoItems 리스트를 observe 하고 리스트를 갱신
    }

    //repository 에 넘겨 viewModel 의 기능 함수 구현
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
