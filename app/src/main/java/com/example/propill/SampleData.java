package com.example.propill;

public class SampleData {
    private String time;
    private String pillname;

    public SampleData(String time, String pillname){
        this.time = time;
        this.pillname = pillname;

    }

    public String getTime()
    {
        return this.time;
    }
    public String getPillname()
    {
        return this.pillname;
    }
}