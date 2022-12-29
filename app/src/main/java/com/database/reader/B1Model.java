package com.database.reader;

public class B1Model {
    private String TypeTask;
    private String Name;
    private String idTask;
    private String Phone;
    private String Task;
    private String Place;
    private String end_date;

    public String getTypeTask() {
        return TypeTask;
    }

    public void setTypeTask(String typeTask) {
        TypeTask = typeTask;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdTask() {
        return idTask;
    }

    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public B1Model(String typeTask, String name, String idTask, String phone, String task, String place, String end_date) {
        TypeTask = typeTask;
        Name = name;
        this.idTask = idTask;
        Phone = phone;
        Task = task;
        Place = place;
        this.end_date = end_date;
    }
}
