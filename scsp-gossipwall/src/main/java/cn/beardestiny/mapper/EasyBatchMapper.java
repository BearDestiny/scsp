package cn.beardestiny.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * @Author BearDestiny
 * @Date 2023/4/20 3:11
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Mapper
public interface EasyBatchMapper<String> extends BaseMapper<String> {

    /**
     * 批量插入，仅适用于mysql
     */
    Long insertBatchSomeColumn(Collection<String> entityList);
}
