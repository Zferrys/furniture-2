package com.furniture.entity;

import java.util.List;

public class Page<T> {
    public static final int PAGE_SIZE = 4;
    private Integer pageNo;
    private Integer pageSize;
    private Integer PageTotalCount;
    private Integer totalRow;
    private List<Furn> items;
    private String url;

    public Page() {
    }

    public Page(Integer pageNo, Integer pageSize, Integer pageTotalCount, Integer totalRow, List<Furn> items, String url) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        PageTotalCount = pageTotalCount;
        this.totalRow = totalRow;
        this.items = items;
        this.url = url;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return PageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        PageTotalCount = pageTotalCount;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public List<Furn> getItems() {
        return items;
    }

    public void setItems(List<Furn> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
