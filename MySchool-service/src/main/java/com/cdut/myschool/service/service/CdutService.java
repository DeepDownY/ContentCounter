package com.cdut.myschool.service.service;

import com.cdut.myschool.service.okHttpTool.StudentGrades;

import java.util.ArrayList;

public interface CdutService {
    ArrayList<ArrayList> getClassList(String name, String password);

    ArrayList<StudentGrades> getGrade(String name, String password);
}
