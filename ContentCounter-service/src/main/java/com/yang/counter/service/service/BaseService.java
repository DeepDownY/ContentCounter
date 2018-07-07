package com.yang.counter.service.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {
    int addOne(T dto);

    int removeOne(String id);

    int updateOne(T dto);

    List<T> queryByParams(Map<String, Object> params);
}
