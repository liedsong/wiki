package com.song.wiki.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserLoginReq {
    @NotEmpty(message = "【用户名】不能为空")
    private String loginName;

//    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$",message = "【密码】至少包含 数字和英文，长度6-32")
    @NotEmpty(message = "【密码】不能为空")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", loginName=").append(loginName);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }
}