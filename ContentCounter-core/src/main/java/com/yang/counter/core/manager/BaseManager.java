package com.yang.counter.core.manager;

import java.util.List;
import java.util.Map;

public interface BaseManager<T> {
    List<T> queryByParams(Map<String, Object> params);

    int deleteByPrimaryKey(String id);

    int insertSelective(T dto);

    T getByPrimaryKey(String id);

    int insertOneRecord(T dto);

    int updateRecordById(T dto);
}
