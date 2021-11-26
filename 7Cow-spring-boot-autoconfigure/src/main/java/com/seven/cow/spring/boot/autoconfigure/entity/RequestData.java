package com.seven.cow.spring.boot.autoconfigure.entity;

public class RequestData extends BasicRequest {

    private String crtUid;
    private String uptUid;
    private String creator;
    private String updater;
    private Long crtTime = System.currentTimeMillis();
    private Long uptTime = System.currentTimeMillis();

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
}
