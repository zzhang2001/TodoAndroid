package com.example.todoandroid;

public class TodoItem {
    private int id;
    private String todo;
    private int important;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }
}
