package com.primus.crm.appointmentplan;

import java.util.Date;

public class AppointmentTime implements  Comparable{

    Date date;
    Date time ;

    int hh;
    int mm;

    int score;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        AppointmentTime time = (AppointmentTime) o;
        return this.getScore() >time.getScore()?1:-1;


    }
}
