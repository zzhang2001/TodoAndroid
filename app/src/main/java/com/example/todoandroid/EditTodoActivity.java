package com.example.todoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class EditTodoActivity extends AppCompatActivity {
    private int id;
    private TodoDbHandler dbHandler;
    private TodoItem todoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        TextView tvTitle = findViewById(R.id.tvTitle);
        EditText edTodo = findViewById(R.id.edTodo);
        CheckBox chImportant = findViewById(R.id.chImportant);
        id = getIntent().getIntExtra("id", -1);
        dbHandler = new TodoDbHandler(this);
        if (id == -1) {
            tvTitle.setText("Add Todo");
        } else {
            tvTitle.setText("Edit Todo");
            todoItem = dbHandler.getTodo(id);
            edTodo.setText(todoItem.getTodo());
            if (todoItem.getImportant() == 1) {
                chImportant.setChecked(true);
            } else {
                chImportant.setChecked(false);
            }
        }
    }

    public void onClick(View view) {
        EditText edTodo = findViewById(R.id.edTodo);
        CheckBox chImportant = findViewById(R.id.chImportant);
        String todo = edTodo.getText().toString();
        int important = chImportant.isChecked() ? 1 : 0;
        if (id == -1) {
            dbHandler.insertTodo(todo, important);
        } else {
            dbHandler.updateTodo(id, todo, important);
        }
        finish();
    }
}
