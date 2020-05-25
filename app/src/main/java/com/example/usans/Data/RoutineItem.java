package com.example.usans.Data;

public class RoutineItem {
    public int id;
    public String category;
    public String routine;

    public RoutineItem(int id, String category, String routine) {
        this.id = id;
        this.category = category;
        this.routine = routine;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoutine() {
        return routine;
    }
    public void setRoutine(String routine) {
        this.routine = routine;
    }
}
