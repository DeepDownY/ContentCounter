package com.cdut.myschool.service.okHttpTool;

/**
 * Created by XuSt on 2017/3/18.
 */
public class StudentGrades {
    private String termid;
    private String className;
    private String teacher;
    private String point;
    private String grade;
    private String gradeType;
    private String gradePoint;

    @Override
    public String toString() {
        return  termid + '\t' +
                className + '\t' +
                teacher + '\t' +
                point + '\t' +
                grade + '\t' +
                gradeType + '\t' +
                gradePoint + '\t';
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(String gradePoint) {
        this.gradePoint = gradePoint;
    }



}
