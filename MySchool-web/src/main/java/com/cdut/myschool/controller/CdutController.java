package com.cdut.myschool.controller;

import com.cdut.myschool.service.okHttpTool.StudentGrades;
import com.cdut.myschool.service.service.CdutService;
import com.cdut.myschool.util.ResultUtil;
import com.cdut.myschool.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/cdut")
public class CdutController {
    @Autowired
    CdutService service;

    @RequestMapping(method = RequestMethod.POST, value = "/getClassList")
    @ResponseBody
    ResultVO getClassList(String name, String password) {
        if (name == null || password == null) {
            return ResultUtil.failure(41011,"name null");
        }
        ArrayList<ArrayList> ls = service.getClassList(name, password);
        if (ls == null) {
            return ResultUtil.failure(41012, "unknown Error");
        } else {
            return ResultUtil.success(ls, 0, ls.size(), "null");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getGrade")
    @ResponseBody
    ResultVO getGrade(String name, String password) {
        if (name == null||password == null) {
            return ResultUtil.failure(41011,"name null");
        }
        ArrayList<StudentGrades> ls = service.getGrade(name, password);
        if (ls == null) {
            return ResultUtil.failure(41012, "account Error");
        } else {
            return ResultUtil.success(ls, 0, ls.size(), "null");
        }
    }
}
