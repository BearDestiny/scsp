package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author BearDestiny
 * @Date 2023/5/3 17:39
 * @Sign “江湖夜雨十年灯”
 * @description: 前端新增弹幕参数
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DanmuParam {
    private String danmu_text;
    private String danmu_color;
}
