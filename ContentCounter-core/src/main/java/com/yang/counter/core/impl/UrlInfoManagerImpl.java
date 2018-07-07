package com.yang.counter.core.impl;


import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yang.counter.core.dto.UrlInfoDto;
import com.yang.counter.core.manager.UrlInfoManager;
import com.yang.counter.core.util.UID;
import com.yang.counter.persist.entity.UrlInfo;
import com.yang.counter.persist.entity.UrlInfoExample;
import com.yang.counter.persist.mapper.UrlInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class UrlInfoManagerImpl implements UrlInfoManager {

    @Autowired
    UrlInfoMapper mapper;

    @Override
    public List<UrlInfoDto> queryByParams(Map<String, Object> params) {
        int size = 15;
        UrlInfoExample example = new UrlInfoExample();
        UrlInfoExample.Criteria criteria = example.createCriteria();
        if (params.get(KEY_URL) != null) {
            criteria.andUrlEqualTo(params.get(KEY_URL).toString());
        }
        if (params.get(KEY_TITLE) != null) {
            criteria.andTitleLike(params.get(KEY_TITLE).toString());
        }

        if (params.get(KEY_PAGE_SIZE) != null && (int)params.get(KEY_PAGE_SIZE) != 0) {
            size = (int) params.get(KEY_PAGE_SIZE);
        }
        if (params.get(KEY_PAGE) != null && (int)params.get(KEY_PAGE) != 0) {
            int start = ((Integer) params.get(KEY_PAGE)-1) * size;
            example.setStart(start);
            example.setLimit(start + size);
        } else {
            example.setStart(0);
            example.setLimit(size);
        }
        List<UrlInfo> infos =  mapper.selectByExample(example);


        return Lists.transform(infos, new com.google.common.base.Function<UrlInfo, UrlInfoDto>() {
            @Override
            public UrlInfoDto apply(UrlInfo urlInfo) {
                UrlInfoDto dto = new UrlInfoDto();
                BeanUtils.copyProperties(urlInfo,dto);
                return dto;
            }
        });
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        if (id != null) {
            return mapper.deleteByPrimaryKey(id);
        }
        return 0;
    }

    @Override
    public int insertSelective(UrlInfoDto dto) {
        return 0;
    }

    @Override
    public UrlInfoDto getByPrimaryKey(String id) {
        return new UrlInfoDto(mapper.selectByPrimaryKey(id));
    }

    @Override
    public int insertOneRecord(UrlInfoDto dto) {
        UrlInfo info = new UrlInfo();
        BeanUtils.copyProperties(dto,info);
        info.setId(UID.next());
        info.setCreateTime(new Date());
        return mapper.insert(info);
    }

    @Override
    public int updateRecordById(UrlInfoDto dto) {
        UrlInfo info = new UrlInfo();
        BeanUtils.copyProperties(dto,info);
        info.setCreateTime(new Date());
        return mapper.updateByPrimaryKey(info);
    }

    @Override
    public Set<String> getUrls() {
        List<UrlInfo> infos = mapper.selectByExample(new UrlInfoExample());
        return Sets.newHashSet(Lists.transform(infos, new Function<UrlInfo, String>() {
            @Override
            public String apply(UrlInfo input) {
                return input.getUrl();
            }
        }));
    }

    @Override
    public List<UrlInfoDto> getAllInfo() {
        List<UrlInfo> infos =  mapper.selectByExample(new UrlInfoExample());

        return Lists.transform(infos, new com.google.common.base.Function<UrlInfo, UrlInfoDto>() {
            @Override
            public UrlInfoDto apply(UrlInfo urlInfo) {
                UrlInfoDto dto = new UrlInfoDto();
                BeanUtils.copyProperties(urlInfo,dto);
                return dto;
            }
        });
    }
}
