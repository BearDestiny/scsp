package cn.beardestiny.controller;

import cn.beardestiny.param.UserRegParam;
import cn.beardestiny.service.UserService;
import cn.beardestiny.utils.RCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author  BearDestiny
 * @Date 2023/4/7 15:02
 * @Sign “江湖夜雨十年灯”
 * @description: 用户注册控制器
 */
@RestController
@RequestMapping("/user")
public class UserRegisterController {

    @Resource
    private UserService userService;


    /**
     * 检查账号是否可用
     */
    @GetMapping("check")
    public RCode check(@RequestParam String user_account){
        //是否通过校验
        String reg = "^[a-zA-Z0-9]{4,16}$";
        if( user_account == null || user_account.equals("") ){
            return RCode.ruleReject("账号为空");
        } else if(!user_account.matches(reg)){
            return RCode.ruleReject("账号不符合规则");
        } else {
            return userService.check(user_account);
        }
    }


    /**
     * 验证本人手机
     */
    @GetMapping("/phoneVerified")
    public RCode phoneVerified(@RequestParam String phone, HttpSession session){
        String phoneVerifyCode = "1234";
        session.setAttribute("phoneVerifyCode", phoneVerifyCode);
        return RCode.pass("验证码发送成功");
    }


    /**
     * 用户注册
     */
    @PostMapping("/register")
    public void userRegister(@Validated UserRegParam userRegParam, BindingResult result,
                              HttpSession session,
                              HttpServletResponse response) throws IOException {

        //用户注册参数校验
        String accountReg = "^[a-zA-Z0-9]{4,16}$";
        String pwdReg = "^(?=.*[a-zA-Z])(?=.*\\d).{8,16}$";
        boolean hasNull = result.hasErrors();
        boolean rightCode = String.valueOf(session.getAttribute("phoneVerifyCode")).equals(userRegParam.getRegVerifyCode());
        boolean rightSecondPwd = Objects.equals(userRegParam.getUser_password(), userRegParam.getUser_secondPwd());
        boolean rightAccount = userRegParam.getUser_account().matches(accountReg);
        boolean rightPwd = userRegParam.getUser_password().matches(pwdReg);
        //参数含空、验证码输入错误、密码规则错误、二次密码不一致、账号规则错误
        if(hasNull || !rightCode || !rightSecondPwd || !rightAccount || !rightPwd){
            response.sendRedirect("http://localhost/error_home");
        } else {
            RCode code = userService.register(userRegParam);
            if(code.getCode().equals("001")){
                response.sendRedirect("http://localhost/regSuccess");
            } else {
                response.sendRedirect("http://localhost/error_home");
            }
        }
    }
}
