package cn.beardestiny.service.Impl;

import cn.beardestiny.mapper.DanmuMapper;
import cn.beardestiny.service.DanmuService;
import cn.beardestiny.param.DanmuParam;
import cn.beardestiny.pojo.Danmu;
import cn.beardestiny.utils.RCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/5/3 19:18
 * @Sign “江湖夜雨十年灯”
 * @description:
 */

@Service
public class DanmuServiceImpl implements DanmuService {

    @Resource
    private DanmuMapper danmuMapper;


    @Override
    public RCode insertDanmu(DanmuParam danmuParam) {
        int num = danmuMapper.insertDanmu(danmuParam);
        if( num > 0 ){
            return RCode.pass("添加成功");
        }
        return RCode.failure("添加失败");
    }

    @Override
    public RCode selectDanmuList() {
        List<Danmu> list = danmuMapper.selectDanmuList();
        if ( list != null ){
            return RCode.pass("查询成功", list);
        }
        return RCode.failure("查询失败");
    }
}
