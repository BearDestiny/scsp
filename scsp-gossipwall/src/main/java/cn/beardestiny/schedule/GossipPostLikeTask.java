package cn.beardestiny.schedule;

import cn.beardestiny.mapper.EasyBatchMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author BearDestiny
 * @Date 2023/4/20 2:20
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子点赞数据定时同步任务
 */

@Slf4j
@Component
public class GossipPostLikeTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private EasyBatchMapper<String> easyBatchMapper;


//    @Scheduled(fixedRate = 360000 *10  * 2)     // 2小时
    public void task(){
        //获取redis 点赞表的值，存入mysql中
        Set<String> likeSet = redisTemplate.opsForSet().members("GossipPostLike");

        //非空判断，继续执行
        if( likeSet != null ){
            List<String> listRecord = new ArrayList<>(likeSet);
            Long res= easyBatchMapper.insertBatchSomeColumn(listRecord);
            log.info(res+"条校园墙点赞记录以同步至MySQL");
        }

    }
}
