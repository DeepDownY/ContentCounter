package com.yang.counter.controller;

import com.yang.counter.core.dto.UrlInfoDto;
import com.yang.counter.service.crawler.CrawlerController;
import com.yang.counter.service.crawler.DataDealer;
import com.yang.counter.service.service.UrlInfoService;
import com.yang.counter.util.ResultUtil;
import com.yang.counter.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/url")
public class UrlInfoController {

    @Autowired
    UrlInfoService service;

    @RequestMapping(method = RequestMethod.GET, value = "/getAllCount")
    @ResponseBody
    public ResultVO getAllCount(String size, String page) {

        int sizeNum = 15;
        int pageNum = 1;
        if (size != null) {
            sizeNum = Integer.parseInt(size);
        }
        if (page != null) {
            pageNum = Integer.parseInt(page);
        }
        List<UrlInfoDto> dtoList = service.getAllCount(pageNum, sizeNum);
        return ResultUtil.success(dtoList, pageNum, sizeNum, null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/start")
    @ResponseBody
    public ResultVO start() {
        service.start();
        return ResultUtil.success();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllCountWithoutPaging")
    @ResponseBody
    public ResultVO getAllCount() {

        List<UrlInfoDto> dtoList = service.getAllCount();
        return ResultUtil.success(dtoList, 0, dtoList.size(), null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUrl")
    @ResponseBody
    public ResultVO getUrl(String url) {
        service.getUrl(url);
        return ResultUtil.success(url, 0, 1, null);
    }
}



