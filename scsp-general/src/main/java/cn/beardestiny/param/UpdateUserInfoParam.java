package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author BearDestiny
 * @Date 2023/5/13 19:00
 * @Sign “江湖夜雨十年灯”
 * @description: 用户信息更新参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoParam {
    private String valuefor;
    private String user_id;
    private String updateValue;
}
