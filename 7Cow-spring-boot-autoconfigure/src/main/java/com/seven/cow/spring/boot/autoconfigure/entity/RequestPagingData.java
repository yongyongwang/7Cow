package com.seven.cow.spring.boot.autoconfigure.entity;

import com.seven.cow.spring.boot.autoconfigure.constant.Conts;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

public class RequestPagingData extends RequestData {

    private int pageNum;

    private int pageSize;

    private transient int total;

    private List<String> asc;

    private List<String> desc;

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

    public String getAsc() {
        if (null != asc && !asc.isEmpty()) {
            return asc.stream().distinct().collect(Collectors.joining(Conts.SPLIT_COMMA));
        }
        return Conts.STRING_EMPTY;
    }

    public void setAsc(List<String> asc) {
        this.asc = asc;
    }

    public String getDesc() {
        if (null != desc && !desc.isEmpty()) {
            return desc.stream().distinct().collect(Collectors.joining(Conts.SPLIT_COMMA));
        }
        return Conts.STRING_EMPTY;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    @Transient
    public int offset() {
        if (this.pageNum <= 0) {
            this.pageNum = 1;
        }
        if (this.pageSize <= 0) {
            this.pageSize = 20;
        }
        return (pageNum - 1) * pageSize;
    }

}
