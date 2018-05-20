package com.cdut.myschool.controller;

import com.cdut.myschool.core.dto.UserDto;
import com.cdut.myschool.core.manager.UserManager;
import com.cdut.myschool.service.service.UserService;
import com.cdut.myschool.util.CodeUtils;
import com.cdut.myschool.util.ResultUtil;
import com.cdut.myschool.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @RequestMapping(method = RequestMethod.POST, value = "/getUser")
    @ResponseBody
    public ResultVO userSearch(String id, String userName) {

        Map<String,Object> map = new HashMap<>();
        if (null != id) {
            map.put(UserManager.KEY_ID, id);
        }
        if (null != userName) {
            map.put(UserManager.KEY_USER_NAME, userName);
        }
        List<UserDto> ls = service.queryByParams(map);

        if (ls != null) {
            return ResultUtil.success(ls,0,0, null);
        } else {
            return ResultUtil.failure(CodeUtils.FAIL_UNKNOWN, CodeUtils.MSG_UNKNOWN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeCoin")
    @ResponseBody
    public ResultVO changeCoin(String id, String coin) {
        if (null == id) {
            return ResultUtil.failure(CodeUtils.FAIL_ID_NULL, CodeUtils.MSG_USER_ID_NULL);
        }
        if (coin == null) {
            return ResultUtil.failure(CodeUtils.FAIL_USER_COIN_NULL, CodeUtils.MSG_COIN_NULL);
        }
        int result = service.updateCoin(id, coin);
        if (result > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.failure(CodeUtils.FAIL_NO_SUCH_LINE, CodeUtils.MSG_NO_SUCH_LINE);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOneUser")
    @ResponseBody
    public ResultVO addUser (String id, String userName, String headPicUrl) {
        if (headPicUrl == null) {
            headPicUrl = "";
        }
        if (!service.checkIDOnly(id)) {
            return ResultUtil.failure(CodeUtils.FAIL_USER_ID_REPEAT, CodeUtils.MSG_USER_ID_REPEAT);
        }
        UserDto dto = new UserDto(id, userName,headPicUrl,0);
        int result = service.addOne(dto);
        if (result > 0) {
            return ResultUtil.success();
        } else {
            return ResultUtil.failure(CodeUtils.FAIL_NO_SUCH_LINE, CodeUtils.MSG_NO_SUCH_LINE);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checkUserName")
    @ResponseBody
    public ResultVO checkUserName (String userName) {
        if (userName == null) {
            return ResultUtil.failure(CodeUtils.FAIL_PARAMENT_ERROR,CodeUtils.MSG_USER_NAME_NULL);
        }
        if (!service.checkIDOnly(userName)) {
            return ResultUtil.failure(CodeUtils.FAIL_USER_ID_REPEAT, CodeUtils.MSG_USER_ID_REPEAT);
        }
        return ResultUtil.success();
    }
}
