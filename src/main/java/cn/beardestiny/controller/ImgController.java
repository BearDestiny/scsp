package cn.beardestiny.controller;

import cn.beardestiny.utils.VerifyCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author BearDestiny
 * @Date 2023/4/9 15:28
 * @Sign “江湖夜雨十年灯”
 * @description: 图片控制器
 */

@Controller
public class ImgController {

    /*
     * 向前端发送登录的验证码图片
     * */
    @GetMapping("/login/getVerifyCode*")
    public void sendVerifyCode(HttpSession session, HttpServletResponse response){
        String logVerifyCode = VerifyCodeUtil.generateVerifyCode(4);
        //会话域存入验证码“verifyCode”
        session.setAttribute("logVerifyCode", logVerifyCode);
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            VerifyCodeUtil.outputImage(100,50, outputStream, logVerifyCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
