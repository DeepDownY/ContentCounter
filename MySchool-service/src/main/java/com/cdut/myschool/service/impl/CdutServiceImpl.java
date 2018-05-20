package com.cdut.myschool.service.impl;

import com.cdut.myschool.service.okHttpTool.CdutEduSys;
import com.cdut.myschool.service.okHttpTool.StudentGrades;
import com.cdut.myschool.service.service.CdutService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class CdutServiceImpl implements CdutService {

    @Override
    public ArrayList<ArrayList> getClassList(String name,String password) {
        ArrayList<ArrayList> ls = null;
        CdutEduSys cdutEduSys = new CdutEduSys();
        if (!cdutEduSys.login(name,password)) {
            return null;
        }
        try {
            ls = cdutEduSys.getClassTable(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public ArrayList<StudentGrades> getGrade(String name, String password)  {
        CdutEduSys cdutEduSys = new CdutEduSys();
        if (cdutEduSys.login(name, password)) {
            ArrayList<StudentGrades> ls = null;
            try {
                ls = cdutEduSys.getGrades();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ls;
        } else {
            return null;
        }
    }


}
