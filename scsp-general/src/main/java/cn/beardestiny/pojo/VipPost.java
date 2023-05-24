package cn.beardestiny.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 2:48
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipPost implements Serializable {
    public static final Long serialVersionUID = 1L;

    private Long id;
    private String post_id;
    private String sender_id;
    private String reciver_id;
    private String reciver_name;
    private String post_img;
    private String post_theme;
    private Date create_time;
    private Date modify_time;
}
