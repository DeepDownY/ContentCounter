package com.cdut.myschool.controller;

import com.cdut.myschool.core.util.UID;
import com.cdut.myschool.util.ResultUtil;
import com.cdut.myschool.vo.ResultVO;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping(method = RequestMethod.POST, value = "/getUser")
    @ResponseBody
    public ResultVO userSearch(String id, String userName) {
        Map<String,String> info = new HashMap<>();
        info.put("name",UID.next());

        String accessKey = "VWYekHyAoM6ElKCN0mwqsNRyhcEOU7GOepDnvqcR";
        String secretKey = "NGA3Lt9p46keUtlALVL1zwBZZugP90l6iQh8YxLh";
        String bucket = "yangping1247518948";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        info.put("token",upToken);
        return ResultUtil.success(info,0,1, null);
    }
}
