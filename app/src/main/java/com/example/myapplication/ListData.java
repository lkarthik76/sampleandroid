package com.example.myapplication;

public class ListData {
    private int lap;
    private int stepcount;

    public ListData(int lap, int stepcount) {
        this.lap = lap;
        this.stepcount = stepcount;
    }

    public int getLap() {
        return lap;
    }

    public int getStepcount() {
        return stepcount;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public void setStepcount(int stepcount) {
        this.stepcount = stepcount;
    }
}