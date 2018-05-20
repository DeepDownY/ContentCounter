package com.cdut.myschool.service.impl;

import com.cdut.myschool.core.dto.UserDto;
import com.cdut.myschool.core.manager.UserManager;
import com.cdut.myschool.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserManager manager;

    @Override
    public int addOne(UserDto dto) {
        return manager.insertOneRecord(dto);
    }

    @Override
    public int removeOne(String id) {
        return manager.deleteByPrimaryKey(id);
    }

    @Override
    public int updateOne(UserDto dto) {
        return manager.updateRecordById(dto);
    }

    @Override
    public List<UserDto> queryByParams(Map<String, Object> params) {
        return manager.queryByParams(params);
    }

    @Override
    public int updateCoin(String id, String coin) {
        return manager.updateCoinById(id, Integer.parseInt(coin));
    }

    @Override
    public boolean checkIDOnly(String name) {
        Map<String, Object> map = new HashMap<>();
        if (name != null) {
            map.put(UserManager.KEY_ID, name);
            return manager.queryByParams(map).size() == 0;
        }
        return false;
    }
}
