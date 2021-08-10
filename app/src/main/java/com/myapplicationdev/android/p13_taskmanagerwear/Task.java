package com.myapplicationdev.android.p13_taskmanagerwear;

import java.io.Serializable;

public class Task implements Serializable {

    private int id;
    private String name;
    private String desc;

    public Task(int id, String name, String desc){
        this.id = id;
        this.desc = desc;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public  void setId(int id){
        this.id = id;
    }

    public String getDesc(){
        return desc;
    }

    public void  setDesc(String desc){
        this.desc = desc;
    }

    @Override
    public String toString() {
        return id + "\n" + desc + "\n" + name;
    }

}
