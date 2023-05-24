package cn.beardestiny.service;

import cn.beardestiny.param.DanmuParam;
import cn.beardestiny.utils.RCode;

/**
 * @Author BearDestiny
 * @Date 2023/5/3 19:18
 * @Sign “江湖夜雨十年灯”
 * @description: 弹幕服务接口
 */
public interface DanmuService {

    RCode insertDanmu(DanmuParam danmuParam);

    RCode selectDanmuList();
}
