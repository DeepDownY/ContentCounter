package com.yang.counter.core.manager;

import com.yang.counter.core.dto.UrlInfoDto;

import java.util.List;
import java.util.Set;

public interface UrlInfoManager extends BaseManager<UrlInfoDto> {
    String KEY_URL = "url";
    String KEY_TITLE = "title";
    String KEY_PAGE = "page";
    String KEY_PAGE_SIZE = "pageSize";

    Set<String> getUrls();

    List<UrlInfoDto> getAllInfo();
}
