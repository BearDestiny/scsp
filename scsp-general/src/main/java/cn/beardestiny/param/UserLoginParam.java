package cn.beardestiny.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author BearDestiny
 * @Date 2023/4/15 20:15
 * @Sign “江湖夜雨十年灯”
 * @description: 前端用户登录参数
 */
@Data
public class UserLoginParam {
    @NotBlank
    private String user_account;
    @NotBlank
    private String user_password;
    @NotBlank
    private String logVerifyCode;
}
