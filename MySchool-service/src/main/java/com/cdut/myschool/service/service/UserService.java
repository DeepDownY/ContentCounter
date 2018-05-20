package com.cdut.myschool.service.service;

import com.cdut.myschool.core.dto.UserDto;

public interface UserService extends BaseService<UserDto> {

    int updateCoin(String id, String coin);

    boolean checkIDOnly(String name);
}
