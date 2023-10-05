package com.example.room.view;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.model.TodoModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TodoModel 의 리스트를 생상자로부터 전달받으며 RecycleView.Adapter 를 상속받고
//RecyclerView.TodoViewHolder 를 ViewHolder 로 갖는 TodoListAdapter 클래스 를 만든다.
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>{
    private List<TodoModel> todoItems = new ArrayList<>();
    private final TodoItemClick deletetItemClick;

    public interface TodoItemClick {
        void onClick(TodoModel todoModel);  //삭제 버튼 클릭시 데이터 삭제
    }

    public TodoListAdapter(TodoItemClick deletetItemClick) {
        this.deletetItemClick = deletetItemClick;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        TodoModel todoModel = todoItems.get(position);
        todoModel.setSeq(position + 1);
        holder.bind(todoModel);
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public void setTodoItems(List<TodoModel> todoItems) {
        this.todoItems = todoItems;
        notifyDataSetChanged();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_seq;
        private final TextView tv_title;
        private final TextView tv_content;
        private final TextView tv_date;
        private final ImageView iv_delete;

        public TodoViewHolder(View itemView) {
            super(itemView);
            tv_seq = itemView.findViewById(R.id.tv_seq);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }

        public void bind(TodoModel todoModel) {
            tv_seq.setText(String.valueOf(todoModel.getSeq()));
            tv_title.setText(todoModel.getTitle());
            tv_content.setText(todoModel.getContent());
            tv_date.setText(convertDateToString(todoModel.getCreateDate(), "yyyy.MM.dd HH:mm"));

            iv_delete.setOnClickListener(view -> deletetItemClick.onClick(todoModel));
        }
    }

    public static String convertDateToString(long timeMillis, String format) {      //현재 날짜
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(timeMillis));
    }
}
