package cn.beardestiny.service.Impl;

import cn.beardestiny.mapper.TalkMapper;
import cn.beardestiny.service.TalkService;
import cn.beardestiny.param.FrontTalk;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author BearDestiny
 * @Date 2023/5/2 4:23
 * @Sign “江湖夜雨十年灯”
 * @description: 交易留言服务实现类
 */
@Service
public class TalkServiceImpl implements TalkService {

    @Resource
    private TalkMapper talkMapper;

    /**
     * 留言发布
     */
    @Override
    public RCode addTalkTo(FrontTalk talk) {
        int num = talkMapper.insertTalk(talk);
        if( num > 0 ){
            return RCode.pass("留言成功");
        }
        return RCode.failure("留言失败");
    }
}
