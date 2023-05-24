package cn.beardestiny.service;

import cn.beardestiny.param.FrontTalk;
import cn.beardestiny.utils.RCode;

/**
 * @Author BearDestiny
 * @Date 2023/5/2 4:23
 * @Sign “江湖夜雨十年灯”
 * @description: 交易留言服务接口
 */
public interface TalkService {

    /**
     * 留言发布
     */
    RCode addTalkTo(FrontTalk talk);
}
