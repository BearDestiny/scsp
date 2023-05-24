package cn.beardestiny.utils;

import cn.beardestiny.feign.UserFeign;
import cn.beardestiny.pojo.FrontUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 19:00
 * @Sign “江湖夜雨十年灯”
 * @description: feign封装，获取uid工具类
 */

@Component
public class FeignGetUid {

    @Resource
    private UserFeign userFeign;

    //根据token获取uid
    public String getUid(String token){

        RCode tokenResCode = userFeign.verifiedUserToken(token);
        if(Objects.equals(tokenResCode.getCode(), "000")){
            return "000";
        }
        //获取token校验中返回的user_id
        List<Object> tokenData =  ObjCastList.castList(tokenResCode.getData(), Object.class);
        HashMap<?,?> map = (LinkedHashMap<?,?>) tokenData.get(1);
        return ( (String) map.get("user_id") ) ;
    }

}
