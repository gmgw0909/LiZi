package com.fancy.learncenter.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sww on 2016/10/9.
 */

public class WeixinPayEntity {

    private String sign;
    private String timestamp;
    private String noncestr;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String appid;

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public String getAppid() {
        return appid;
    }



}
