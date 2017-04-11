package com.payinxl.common.msg;

import java.io.Serializable;

/**
 * Created by barry
 * Date:2017/2/15
 */
public class ResMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String info;
    private String acturl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getActurl() {
        return acturl;
    }

    public void setActurl(String acturl) {
        this.acturl = acturl;
    }
}
