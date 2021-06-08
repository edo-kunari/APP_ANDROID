package com.example.app_android;

public class Task {
    private String headliner, description;
    public Task(){

    }
    public Task(String strHeadliner, String strDescription){
        this.headliner = strHeadliner;
        this.description = strDescription;
    }
    public String getHeadliner(){
        return this.headliner;
    }
    public void setHeadliner(String strHeadliner){
        this.headliner = strHeadliner;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String strDescription){
        this.description = strDescription;
    }
}
