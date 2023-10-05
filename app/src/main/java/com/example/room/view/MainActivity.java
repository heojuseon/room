package com.example.room.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.room.R;
import com.example.room.model.TodoModel;
import com.example.room.viewmodel.TodoViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//VIew : UI Controller 를 담당하는 Activity, Fragment
//사용자와 상호작용하고 데이터의 변화를 감지하기 위한 observer 를 가지고 있다.
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TodoViewModel todoViewModel;

    private TodoListAdapter todoListAdapter;
    private final List<TodoModel> todoItems = new ArrayList<>();

    private RecyclerView recyclerview_list;
    private Button btn_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "== onCreate ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initRecyclerview();
        initBtnAdd();
    }

    private void initViewModel() {  //observe 를 사용하여 데이터 변경시 adapter 에 전달, 변경사항 있을시 변경되어 화면 출력
        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TodoViewModel.class);
        todoViewModel.getTodoListAll().observe(this, new Observer<List<TodoModel>>() {
            @Override
            public void onChanged(List<TodoModel> todoModels) {
                todoListAdapter.setTodoItems(todoModels);
            }
        });
    }

    private void initRecyclerview() {
        todoListAdapter = new TodoListAdapter(new TodoListAdapter.TodoItemClick() {
            @Override
            public void onClick(TodoModel todoModel) {
                deleteDialog(todoModel);
            }
        });
        recyclerview_list = findViewById(R.id.recyclerview_list);
        recyclerview_list.setHasFixedSize(true);
        recyclerview_list.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_list.setAdapter(todoListAdapter);
    }

    private void initBtnAdd() {
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(view -> showAddTodoDialog());
    }

    //다이얼로그 생성하여 작업처리
    private void showAddTodoDialog() {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add, null);
        EditText et_add_title = dialogView.findViewById(R.id.et_add_title);
        EditText et_add_content = dialogView.findViewById(R.id.et_add_content);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.setTitle("Todo 아이템 추가하기")
                .setView(dialogView)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Long id = null;
                        String title = et_add_title.getText().toString();
                        String content = et_add_content.getText().toString();
                        Long createdDate = new Date().getTime();

                        TodoModel todoModel = new TodoModel(id, todoListAdapter.getItemCount() + 1, title, content, createdDate);
                        todoViewModel.insert(todoModel);
                    }
                })
                .setNegativeButton("취소", null)
                .create();
        dialog.show();
    }

    private void deleteDialog(TodoModel todoModel) {    //다이얼로그에서 해당 데이터 삭제
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(todoModel.getSeq() + " 번 Todo 아이템을 삭제할까요? ");
        builder.setNegativeButton("취소", null);
        builder.setPositiveButton("확인", (dialogInterface, i) -> {
            todoViewModel.delete(todoModel);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}