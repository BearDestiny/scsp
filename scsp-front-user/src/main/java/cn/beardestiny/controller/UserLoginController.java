package cn.beardestiny.controller;

import cn.beardestiny.config.JwtConfig;
import cn.beardestiny.param.UserLoginParam;
import cn.beardestiny.pojo.FrontUser;
import cn.beardestiny.pojo.User;
import cn.beardestiny.service.UserService;
import cn.beardestiny.utils.RCode;
import cn.beardestiny.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author BearDestiny
 * @Date 2023/4/12 15:15
 * @Sign “江湖夜雨十年灯”
 * @description: 用户登录控制器
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Resource
    private JwtConfig jwt;

    @Value("${RSAKEY.PUBLIC_KEY}")
    private String PUBLIC_KEY;

    @Value("${RSAKEY.PRIVATE_KEY}")
    private String PRIVATE_KEY;

    @Resource
    private UserService userService;


    /**
     * 向前端发送RSA登录公钥
     */
    @GetMapping("/getLoginPublicKey")
    public String sendRsaPublicKey(){
        return PUBLIC_KEY;
    }


    /**
     * 用户登录
     * @Param logVerifyCode 登录验证码 session:"logVerifyCode"
     * @Param user_password  格式为RSA密文
     */
    @PostMapping("/login")
    public RCode userLogin(@Validated UserLoginParam userLoginParam, BindingResult result,
                                                HttpSession session) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //含空直接返回错误
        boolean hasNull = result.hasErrors();
        if( hasNull ){
            return RCode.failure("登录失败");
        }

        //RSA解密
        String user_password = RSAUtil.privateDecrypt(userLoginParam.getUser_password(), RSAUtil.getPrivateKey(PRIVATE_KEY));
        String sessionVerifyCode = (String) session.getAttribute("logVerifyCode");

        //验证码、密码一致，登录通过
        RCode loginRes = userService.login( userLoginParam.getUser_account(), user_password );       //返回登录结果 和 user_id

        boolean pwdPass = Objects.equals(loginRes.getCode(), "001");
        boolean verifyCodePass = (userLoginParam.getLogVerifyCode().equals(sessionVerifyCode.toLowerCase()));

        if(  pwdPass && verifyCodePass ){
            //颁布userToken
            String userToken = jwt.createToken("user", (String)loginRes.getData() );
            //移除会话域中的验证码
            session.removeAttribute("logVerifyCode");
            return RCode.pass("登录成功", userToken);
        } else if ( !verifyCodePass ) {
            return RCode.ruleReject("验证码错误");
        } else if( !pwdPass ){
            return RCode.ruleReject("账号或密码错误");
        }
        return RCode.failure("登录失败");
    }


    /**
     * 用户退出
     */
    @GetMapping("/logout")
    public RCode userLogout(HttpSession session){
        session.removeAttribute("user_account");
        System.out.println(session.getId());
        return RCode.pass("退出成功");
    }


    /**
     * 用户登录状态校验
     */
    @GetMapping("/verifiedUserToken")
    public RCode verifiedUserToken(@RequestHeader(value = "userToken", required = false) String userToken){

        Date tokenExpirationTime = jwt.getExpirationDateFromToken(userToken);

        //用户已登录 且 token未失效，token续期
        if( userToken != null && !jwt.isTokenExpired(tokenExpirationTime)){
            String user_id = jwt.getUser_id(userToken);
            String newToken = jwt.createToken("user", user_id);

            //根据uid 查询 user
            User user = (User)( userService.selectUserById( user_id).getData() );

            List<Object> list = new ArrayList<>();
            FrontUser frontUser = new FrontUser(user.getUser_id(), user.getUser_account(), user.getUser_nickname(), user.getUser_headImg());

            list.add(newToken);
            list.add(frontUser);

            return RCode.pass("用户已登录", list);
        } else {
            //用户未登录或 token过期
            return RCode.failure("用户未登录");
        }
    }

}
