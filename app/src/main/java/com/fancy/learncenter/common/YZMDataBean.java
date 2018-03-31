package com.fancy.learncenter.common;

import java.util.List;

/**
 * Created by hyy on 2017/12/26.
 * describe as
 */

public class YZMDataBean {


    /**
     * code : 0
     * message : 发送成功
     * data :
     * links : []
     */

    private int code;
    private String message;
    private String data;
    private List<?> links;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<?> getLinks() {
        return links;
    }

    public void setLinks(List<?> links) {
        this.links = links;
    }
}
