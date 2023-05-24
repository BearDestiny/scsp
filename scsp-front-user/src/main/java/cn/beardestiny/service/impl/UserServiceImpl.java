package cn.beardestiny.service.impl;

import cn.beardestiny.mapper.UserMapper;
import cn.beardestiny.param.TalkParam;
import cn.beardestiny.param.UpdateUserInfoParam;
import cn.beardestiny.param.UserRegParam;
import cn.beardestiny.pojo.FrontUser;
import cn.beardestiny.pojo.User;
import cn.beardestiny.pojo.FrontUserInfo;
import cn.beardestiny.service.UserService;
import cn.beardestiny.utils.RCode;
import cn.beardestiny.utils.SnowflakeIdWorker;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.power.common.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/7 16:32
 * @Sign “江湖夜雨十年灯”
 * @description: 用户业务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Value("${AESKEY}")
    private String AESKEY;

    /**
     * 检查账号是否可用
     * （查询数据库该账号是否已存在）
     * @param user_account 账号，已校验规则
     * @return RCode
     */
    @Override
    public RCode check(String user_account) {
        //封装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", user_account);
        //数据库查询
        Long selectCount = userMapper.selectCount(queryWrapper);
        //查询结果处理
        if(selectCount == 0){
            //数据库中不存在，允许使用
            return RCode.pass("账号可用");
        }
        return RCode.failure("账号已存在");
    }

    /**
     * 用户账号注册
     * （增加用户记录）
     * @param userRegParam 注册参数
     * @return RCode
     */
    @Override
    public RCode register(UserRegParam userRegParam) {
        SnowflakeIdWorker snowId = new SnowflakeIdWorker(0);

        User user = new User();
        user.setUser_id("uid"+snowId.nextId());
        user.setUser_account(userRegParam.getUser_account());
        //AES加密密码
        user.setUser_password(AESUtil.encodeByECB(userRegParam.getUser_password(), AESKEY));
        user.setUser_phone(userRegParam.getUser_phone());
        user.setUser_headImg("img/default.jpg");
        user.setUser_nickname("用户"+(int)((Math.random() * 9+1) * 10000));
        user.setUser_currency(50L);
        int res = userMapper.insert(user);
        int res2 = userMapper.InsertUserInfo(user.getUser_id());
        //注册成功
        if(res ==1 && res2 ==1){
            log.info("UserServiceImpl.register 业务执行完毕，结果：{}","注册成功！");
            return RCode.pass("注册成功");
        }
        log.info("UserServiceImpl.register 业务执行完毕，结果：{}","注册失败！");
        return RCode.failure("注册失败");
    }


    /**
     * 用户登录
     *（查询用户密码）
     */
    @Override
    public RCode login(String user_account ,String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", user_account);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return RCode.failure("账号不存在");
        }
        //AES解密
        String realPwd = AESUtil.decodeByECB(user.getUser_password(), AESKEY);
        if( realPwd.equals(password)){
            return RCode.pass("登录成功",user.getUser_id());
        }
        return RCode.failure("密码错误");
    }


    /**
     * 根据账号查询单个用户
     */
    @Override
    public RCode selectUserById(String user_id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user_id);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return RCode.failure("用户查询失败");
        }
        return RCode.pass("用户查询成功", user);
    }

    /**
     * 用户名查询单个用户
     *
     * @param nickname
     */
    @Override
    public RCode selectUserByNickname(String nickname) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_nickname", nickname);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return RCode.failure("用户查询失败");
        }
        return RCode.pass("用户查询成功", user);
    }

    /**
     * 查询前端用户实体类
     *
     * @param user_id
     */
    @Override
    public RCode selectFrontUser(String user_id) {
        FrontUser user = userMapper.selectFrontUser(user_id);
        if( user != null ){
            return RCode.pass("查询成功", user);
        }
        return RCode.failure("查询失败");
    }

    /**
     * 获取用户完善信息
     *
     * @param user_id
     */
    @Override
    public RCode selectUserInfoById(String user_id) {
        FrontUserInfo info = userMapper.selectUserInfo(user_id);
        System.out.println(info);
        if( info != null ){
            return RCode.pass("查询完毕", info);
        }
        return RCode.pass("查询为空");
    }

    /**
     * 更新用户信息
     */
    @Override
    public RCode updateUserInfo(UpdateUserInfoParam infoParam) {
        int num = userMapper.updateUserInfo(infoParam);
        if (num > 0){
            return RCode.pass("更新成功");
        }
        return RCode.failure("更新失败");
    }

    /**
     * 获取用户留言
     */
    @Override
    public RCode getUserTalk(String listener_id) {
        List<TalkParam> talkList =  userMapper.selectTalkByUid(listener_id);
        if( talkList.size() > 0 ){
            return RCode.pass("查询成功", talkList);
        }
        return RCode.failure("查询为空");
    }

    /**
     * 已读留言
     *
     * @param id
     */
    @Override
    public RCode updateTalk(Long id) {
        int num = userMapper.updateTalkById(id);
        if( num > 0 ){
            return RCode.pass("更新成功");
        }
        return RCode.failure("更新失败");
    }

}
