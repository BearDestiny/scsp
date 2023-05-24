package cn.beardestiny.service;

import cn.beardestiny.utils.RCode;

/**
 * @Author BearDestiny
 * @Date 2023/4/19 22:25
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子的Redis服务接口
 */
public interface GossipPostRedisService {

    /**
     * 清空Redis中 40条最新帖子
     */
    RCode unlink(String postListName);
}
