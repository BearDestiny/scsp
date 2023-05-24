package cn.beardestiny.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/3 19:38
 * @Sign “江湖夜雨十年灯”
 * @description: 弹幕实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Danmu {
    private Long id;
    private String danmu_text;
    private String danmu_color;
    private Date create_time;
    private Date modify_time;
}
