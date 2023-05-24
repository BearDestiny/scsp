package cn.beardestiny.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Title: UserRegParam
 * @Author BearDestiny
 * @Package cn.beardestiny.param
 * @Date 2023/4/7 14:47
 * @description: 前端用户注册参数
 *  TODO：要使用jsr 303的注解 进行参数校验
 */
@Data
public class UserRegParam {
    @NotBlank   // 配合controller参数获取中的 @Validated 一起使用
    private String user_account;    //注意，参数名称要等于前端传递的json key
    @NotBlank
    private String user_password;
    @NotBlank
    private String user_secondPwd;
    @NotBlank
    private String user_phone;
    @NotBlank
    private String regVerifyCode;
}
