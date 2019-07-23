package com.example.todoandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    public int selectedId;
    private ArrayList<TodoItem> todoItems;

    public TodoAdapter(ArrayList<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View todoView = inflater.inflate(R.layout.todo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(todoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem todoItem = todoItems.get(position);
        holder.tvItemTodo.setText(todoItem.getTodo());
        holder.tvItemTodo.setTag(todoItem.getId());
        if (todoItem.getImportant() == 1) {
            holder.tvItemTodo.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView tvItemTodo;

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Activity activity = (Activity)view.getContext();
            activity.getMenuInflater().inflate(R.menu.menu_context, contextMenu);
            selectedId = (int)view.findViewById(R.id.tvItemTodo).getTag();
        }

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemTodo = itemView.findViewById(R.id.tvItemTodo);
            itemView.setOnCreateContextMenuListener(this);
        }
    }
}
