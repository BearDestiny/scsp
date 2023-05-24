package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author BearDestiny
 * @Date 2023/5/2 4:21
 * @Sign “江湖夜雨十年灯”
 * @description: 前端留言参数类
 */

@Data
@AllArgsConstructor
public class FrontTalk {
    private String speaker_id;
    private String listener_id;
    private String message;
}
