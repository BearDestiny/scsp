package cn.beardestiny.mapper;

import cn.beardestiny.param.DanmuParam;
import cn.beardestiny.pojo.Danmu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/3 19:19
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
public interface DanmuMapper {

    @Insert("insert into `market_danmu`(danmu_text, danmu_color) values (#{danmu_text},#{danmu_color})")
    int insertDanmu(DanmuParam danmuParam);

    @Select("select * from `market_danmu` order by create_time ASC")
    List<Danmu> selectDanmuList();
}
