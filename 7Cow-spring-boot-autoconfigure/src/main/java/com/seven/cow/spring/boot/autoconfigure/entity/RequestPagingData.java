package com.seven.cow.spring.boot.autoconfigure.entity;

public class RequestPagingData extends RequestData {

    private int pageNum;

    private int pageSize;

    private int total;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        if (this.pageNum <= 0) {
            this.pageNum = 1;
        }
        if (this.pageSize <= 0) {
            this.pageSize = 20;
        }
        return (pageNum - 1) * pageSize;
    }

}
