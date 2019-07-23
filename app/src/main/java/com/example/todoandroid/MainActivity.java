package com.example.todoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TodoItem> todoItems;
    private RecyclerView rvTodo;
    TodoDbHandler dbHandler;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodo = findViewById(R.id.rvTodo);
        dbHandler = new TodoDbHandler(this);
        todoItems = dbHandler.getAllTodos();
        todoAdapter = new TodoAdapter(todoItems);
        rvTodo.setAdapter(todoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTodo.setLayoutManager(linearLayoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(rvTodo.getContext(),
                linearLayoutManager.getOrientation());
        rvTodo.addItemDecoration(decoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        todoItems = dbHandler.getAllTodos();
        todoAdapter = new TodoAdapter(todoItems);
        rvTodo.setAdapter(todoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAddTodo:
                Intent intent = new Intent(this, EditTodoActivity.class);
                startActivity(intent);
                return true;
            case R.id.mnExit:
                finish();
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete todo")
                        .setMessage("Are you sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHandler.deleteTodo(todoAdapter.selectedId);
                                todoItems = dbHandler.getAllTodos();
                                todoAdapter = new TodoAdapter(todoItems);
                                rvTodo.setAdapter(todoAdapter);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();

                return true;
            case R.id.mnEdit:
                Intent intent = new Intent(this, EditTodoActivity.class);
                intent.putExtra("id", todoAdapter.selectedId);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
