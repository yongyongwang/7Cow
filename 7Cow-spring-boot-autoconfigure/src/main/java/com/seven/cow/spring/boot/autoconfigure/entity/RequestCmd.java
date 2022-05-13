package com.seven.cow.spring.boot.autoconfigure.entity;

import com.seven.cow.spring.boot.autoconfigure.constant.Conts;

import java.util.List;
import java.util.stream.Collectors;

public class RequestCmd extends BasicRequest {

    private String crtUid;
    private String uptUid;
    private Long crtId;
    private Long uptId;
    private String creator;
    private String updater;
    private Long crtTime = System.currentTimeMillis();
    private Long uptTime = System.currentTimeMillis();
    private List<String> asc;
    private List<String> desc;

    public String getCrtUid() {
        return crtUid;
    }

    public void setCrtUid(String crtUid) {
        this.crtUid = crtUid;
    }

    public String getUptUid() {
        return uptUid;
    }

    public void setUptUid(String uptUid) {
        this.uptUid = uptUid;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public Long getUptId() {
        return uptId;
    }

    public void setUptId(Long uptId) {
        this.uptId = uptId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Long getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Long crtTime) {
        this.crtTime = crtTime;
    }

    public Long getUptTime() {
        return uptTime;
    }

    public void setUptTime(Long uptTime) {
        this.uptTime = uptTime;
    }

    public String getAsc() {
        if (null != asc && !asc.isEmpty()) {
            return asc.stream().distinct().map(value -> value + " asc").collect(Collectors.joining(Conts.SPLIT_COMMA));
        }
        return Conts.STRING_EMPTY;
    }

    public void setAsc(List<String> asc) {
        if (null != asc && !asc.isEmpty()) {
            asc = asc.stream()
                    .filter(value -> null != value && !value.equals(""))
                    .map(value -> {
                        value = value.replace("asc", "").trim();
                        return value;
                    }).collect(Collectors.toList());
        }
        this.asc = asc;
    }

    public String getDesc() {
        if (null != desc && !desc.isEmpty()) {
            return desc.stream().distinct().map(value -> value + " desc").collect(Collectors.joining(Conts.SPLIT_COMMA));
        }
        return Conts.STRING_EMPTY;
    }

    public void setDesc(List<String> desc) {
        if (null != desc && !desc.isEmpty()) {
            desc = desc.stream()
                    .filter(value -> null != value && !value.equals(""))
                    .map(value -> {
                        value = value.replace("desc", "").trim();
                        return value;
                    }).collect(Collectors.toList());
        }
        this.desc = desc;
    }
}
