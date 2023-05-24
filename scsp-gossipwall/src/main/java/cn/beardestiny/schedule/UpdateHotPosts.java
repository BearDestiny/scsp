package cn.beardestiny.schedule;

import cn.beardestiny.param.GossipPostItem;
import cn.beardestiny.service.GossipPostRedisService;
import cn.beardestiny.service.GossipPostService;
import cn.beardestiny.utils.RCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author BearDestiny
 * @Date 2023/5/13 8:33
 * @Sign “江湖夜雨十年灯”
 * @description: 定时更新40帖
 */
@Slf4j
@Component
public class UpdateHotPosts {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private GossipPostRedisService redisService;

    @Resource
    private GossipPostService postService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Scheduled( cron = "0 0/2 * * * ?")
    @SuppressWarnings("unchecked")
    public void update() throws Exception {
        //mysql获取40个最新帖子
        RCode resCode = postService.selectGossipPostsByNum(40);
        System.out.println(resCode.getCode());
        // 获取成功
        if(Objects.equals(resCode.getCode(), "001")){
            List<GossipPostItem> postList = (ArrayList<GossipPostItem>) postService.selectGossipPostsByNum(40).getData();
            List<String> stringList = new ArrayList<String>();
            String json = null;
            String listKey = "GossipPost";
            // 先异步清除原有
            redisService.unlink(listKey);
            for ( GossipPostItem item : postList ) {
                json = MAPPER.writeValueAsString( item );
                stringList.add(json);
            }
            // 40帖子放入缓存
            Long l = redisTemplate.opsForList().rightPushAll(listKey, stringList);
            log.info( l + "条校园墙帖子定时同步成功！");
        }
    }
}
