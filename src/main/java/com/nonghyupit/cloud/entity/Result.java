package com.nonghyupit.cloud.entity;

import java.util.ArrayList;
import java.util.Date;

public class Result {
    public String type;
    public int count;
    public String from;
    public String to;
    public ArrayList<Date> times = new ArrayList<>();
    public long diff;
}
