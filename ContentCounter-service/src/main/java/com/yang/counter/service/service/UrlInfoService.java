package com.yang.counter.service.service;

import com.yang.counter.core.dto.UrlInfoDto;

import java.util.List;

public interface UrlInfoService {
    void getCountByUrl(String url);

    List<UrlInfoDto> getAllCount(int page, int size);

    List<UrlInfoDto> getAllCount();

    void start();

    void getUrl(String url);
}
