package cn.beardestiny.mapper;

import cn.beardestiny.param.FrontTalk;
import cn.beardestiny.pojo.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @Author BearDestiny
 * @Date 2023/5/2 4:22
 * @Sign “江湖夜雨十年灯”
 * @description: 交易留言mapper
 */
public interface TalkMapper extends BaseMapper<Talk> {

    @Insert("insert into `talk`(speaker_id, listener_id, message) values(#{speaker_id}, #{listener_id}, #{message})")
    int insertTalk(FrontTalk talk);
}
