package cn.beardestiny.service.impl;

import cn.beardestiny.service.GossipPostRedisService;
import cn.beardestiny.utils.RCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/4/19 22:28
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子redis服务接口实现类
 */
@Service
public class GossipPostRedisServiceImpl implements GossipPostRedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 清空Redis中 40条最新帖子
     */
    @Override
    public RCode unlink(String postListName) {
        //异步清除
        Boolean b = redisTemplate.unlink(postListName);
        if( b ){
            return RCode.pass("删除成功");
        }
        return RCode.failure("删除失败");
    }
}
