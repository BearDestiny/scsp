package cn.beardestiny.mapper;

import cn.beardestiny.param.TalkParam;
import cn.beardestiny.param.UpdateUserInfoParam;
import cn.beardestiny.pojo.FrontUser;
import cn.beardestiny.pojo.User;
import cn.beardestiny.pojo.FrontUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/7 16:37
 * @Sign “江湖夜雨十年灯”
 * @description:
 */

@Repository
public interface UserMapper extends BaseMapper<User> {

    FrontUser selectFrontUser(@Param("uid") String user_id);

    @Select("select u.user_phone, u.user_sno, info.user_qq, info.user_wechat , info.user_other, info.user_zy\n" +
            "from `user_info` info\n" +
            "join user u on u.user_id = info.user_id\n" +
            "where info.user_id = #{uid}")
    FrontUserInfo selectUserInfo(@Param("uid") String user_id);

    @Insert("insert into `user_info`(user_id) values(#{uid}) ")
    int InsertUserInfo(@Param("uid") String user_id);

    int updateUserInfo(@Param("param")UpdateUserInfoParam infoParam);

    @Update("update `user` set user_currency = user_currency - #{num} where user_id = #{uid}")
    int updateUserCurrency(@Param("num") Long num, @Param("uid") String uid);

    /**
     * 查询未读消息
     */
    @Select("SELECT t.id, u.user_nickname as speaker_name, u.user_headImg, t.message, t.create_time\n" +
            "FROM `talk` t\n" +
            "join user u on u.user_id = t.speaker_id\n" +
            "where t.listener_id = #{uid} and t.msg_status = 0")
    List<TalkParam> selectTalkByUid(@Param("uid")String listener_id);

    /**
     * 更新消息状态
     */
    @Update("update `talk` set msg_status = 1 where id = #{id} ")
    int updateTalkById(@Param("id")Long id);

}
