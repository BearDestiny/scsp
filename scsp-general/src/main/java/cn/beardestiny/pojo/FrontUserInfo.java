package cn.beardestiny.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/13 17:25
 * @Sign “江湖夜雨十年灯”
 * @description: 用户信息
 */
@Data
@AllArgsConstructor
public class FrontUserInfo {
    private String user_phone;
    private String user_sno;
    private String user_qq;
    private String user_wechat;
    private String user_other;
    private String user_zy;
}
