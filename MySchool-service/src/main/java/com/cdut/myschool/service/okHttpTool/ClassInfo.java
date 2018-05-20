package com.cdut.myschool.service.okHttpTool;

/**
 * Created by XuSt on 2017/3/18.
 */
public class ClassInfo {
    private int start;
    private int end;
    private int lastTime;
    private String lessonName;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    private String dayOfWeek;
    private int week;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "ClassInfo{" +
                "start=" + start +
                ", end=" + end +
                ", lastTime=" + lastTime +
                ", lessonName='" + lessonName + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}
