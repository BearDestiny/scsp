package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/13 20:48
 * @Sign “江湖夜雨十年灯”
 * @description: 我的商品前端参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyGood {
    private Date create_time;
    private String cover_img;
    private String good_name;
    private Double good_price;
    private Integer good_number;
    private Integer good_status;
}
