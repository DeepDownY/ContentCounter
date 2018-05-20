package com.cdut.myschool.core.impl;

import com.cdut.myschool.core.dto.UserDto;
import com.cdut.myschool.core.manager.UserManager;
import com.cdut.myschool.core.util.UID;
import com.cdut.myschool.persist.entity.UserInfo;
import com.cdut.myschool.persist.entity.UserInfoExample;
import com.cdut.myschool.persist.mapper.UserInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserManagerImpl implements UserManager {

    @Autowired
    UserInfoMapper mapper;

    @Override
    public List<UserDto> queryByParams(Map<String, Object> params) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        if (params != null && params.size() != 0) {
            if (params.get(KEY_ID) != null) {
                criteria.andIdEqualTo(params.get(KEY_ID).toString());
            }
            if (params.get(KEY_USER_NAME) != null) {
                criteria.andUserNameEqualTo(params.get(KEY_USER_NAME).toString());
            }
            List<UserInfo> temp = mapper.selectByExample(example);
            List<UserDto> result = new ArrayList<>(temp.size());
            for (UserInfo entity : temp) {
                UserDto dto = new UserDto();
                BeanUtils.copyProperties(entity, dto);
                result.add(dto);
            }
            return result;
        } else {
            throw new IllegalArgumentException("Null User ID");
        }
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(UserDto dto) {
        return 0;
    }

    @Override
    public UserDto getByPrimaryKey(String id) {
        UserInfo info = mapper.selectByPrimaryKey(id);
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(info, dto);
        return dto;
    }

    @Override
    public int insertOneRecord(UserDto dto) {
        UserInfo temp = new UserInfo();
        BeanUtils.copyProperties(dto, temp);
        temp.setCoin(0);
        return mapper.insert(temp);
    }

    @Override
    public int updateRecordById(UserDto dto) {
        UserInfo temp = new UserInfo();
        BeanUtils.copyProperties(dto, temp);
        return mapper.updateByPrimaryKey(temp);
    }

    @Override
    public int updateCoinById(String id, int coin) {
        UserInfo info = mapper.selectByPrimaryKey(id);
        if (info == null) {
            throw new IllegalArgumentException("null value");
        }
        info.setCoin(coin);
        return mapper.updateByPrimaryKey(info);
    }

}
