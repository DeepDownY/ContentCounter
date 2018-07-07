package com.yang.counter.core.dto;

import com.yang.counter.persist.entity.UrlInfo;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class UrlInfoDto {

    public UrlInfoDto() { }

    public UrlInfoDto(UrlInfo info) {
        if (info != null) {
            BeanUtils.copyProperties(info, this);
        }
    }

    private String id;

    private String title;

    private String url;

    private Integer charNum;

    private Integer chineseNumber;

    private Integer markNum;

    private Integer totalNum;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getCharNum() {
        return charNum;
    }

    public void setCharNum(Integer charNum) {
        this.charNum = charNum;
    }

    public Integer getChineseNumber() {
        return chineseNumber;
    }

    public void setChineseNumber(Integer chineseNumber) {
        this.chineseNumber = chineseNumber;
    }

    public Integer getMarkNum() {
        return markNum;
    }

    public void setMarkNum(Integer markNum) {
        this.markNum = markNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}