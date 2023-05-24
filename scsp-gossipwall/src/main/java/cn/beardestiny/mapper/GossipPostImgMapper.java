package cn.beardestiny.mapper;

import cn.beardestiny.utils.RCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author BearDestiny
 * @Date 2023/4/19 17:48
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子图片Mapper
 */
@Repository
public interface GossipPostImgMapper {

    @Insert("INSERT INTO `gossippost_img`(post_id, post_img)\n"+
                    "values${va};")
    int insertPostImg(@Param("va") String values);
}
