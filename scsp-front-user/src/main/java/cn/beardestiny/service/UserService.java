package cn.beardestiny.service;

import cn.beardestiny.param.UpdateUserInfoParam;
import cn.beardestiny.param.UserRegParam;
import cn.beardestiny.utils.RCode;

/**
 * @Author BearDestiny
 * @Date 2023/4/7 16:28
 * @Sign “江湖夜雨十年灯”
 * @description: 用户业务类
 */
public interface UserService {

    /**
     * 检查账号是否可用
     * @param user_account    账号，已通过规则校验
     * @return  RCode
     */
    RCode check(String user_account);


    /**
     * 用户账号注册
     * @param userRegParam    用户注册参数接收实体类（general包）
     */
    RCode register(UserRegParam userRegParam);

    /**
     * 用户登录
     */
    RCode login(String user_acount, String password);

    /**
     * id查询单个用户
     */
    RCode selectUserById(String user_id);

    /**
     * 用户名查询单个用户
     */
    RCode selectUserByNickname(String nickname);

    /**
     * 查询前端用户实体类
     */
    RCode selectFrontUser(String user_id);

    /**
     * 获取用户完善信息
     */
    RCode selectUserInfoById(String user_id);

    /**
     * 更新用户信息
     */
    RCode updateUserInfo(UpdateUserInfoParam infoParam);

    /**
     * 获取用户留言
     */
    RCode getUserTalk(String listener_id);

    /**
     * 已读留言
     */
    RCode updateTalk(Long id);

}
