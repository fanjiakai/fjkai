package com.ddian.youfan.admin.vo;

/**
 * @author grandtech
 * @email 1992lcg@163.com
 * @date 2018-06-06 14:40:36
 */
public class UserVO {
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;


    public String getPwdOld() {
        return pwdOld;
    }

    public void setPwdOld(String pwdOld) {
        this.pwdOld = pwdOld;
    }

    public String getPwdNew() {
        return pwdNew;
    }

    public void setPwdNew(String pwdNew) {
        this.pwdNew = pwdNew;
    }
}
