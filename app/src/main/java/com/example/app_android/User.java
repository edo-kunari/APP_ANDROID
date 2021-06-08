package com.example.app_android;

public class User {
    private String email, tasks, adminStatus;

    public User(String email, String tasks, String adminStatus) {
        this.email = email;
        this.tasks = tasks;
        this.adminStatus = adminStatus;
    }
    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }
}
