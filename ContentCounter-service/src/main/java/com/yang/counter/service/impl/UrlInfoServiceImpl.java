package com.yang.counter.service.impl;

import com.google.common.collect.Maps;
import com.yang.counter.core.dto.UrlInfoDto;
import com.yang.counter.core.manager.UrlInfoManager;
import com.yang.counter.service.crawler.CrawlerController;
import com.yang.counter.service.crawler.DataDealer;
import com.yang.counter.service.service.UrlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UrlInfoServiceImpl implements UrlInfoService {

    @Autowired
    UrlInfoManager manager;

    @Override
    public void getCountByUrl(String url) {
        try {
            new CrawlerController().dealOneCrawl(url,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UrlInfoDto> getAllCount(int page, int size) {
        HashMap<String,Object> map = Maps.newHashMap();
        map.put(UrlInfoManager.KEY_PAGE_SIZE,size);
        map.put(UrlInfoManager.KEY_PAGE,page);
        return manager.queryByParams(map);
    }

    @Override
    public List<UrlInfoDto> getAllCount() {
        return manager.getAllInfo();
    }

    @Override
    public void start() {
        new Thread() {
            @Override
            public void run() {
                try {
                    new CrawlerController().dealOneCrawl("http://news.163.com/");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void getUrl(String url) {
        try {
            new CrawlerController().dealOneCrawl(url,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
