package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/13 23:06
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrder {
    private String order_id;
    private String good_name;
    private String cover_img;
    private Integer buy_num;
    private Double good_price;
    private String seller_nickname;
    private Integer order_status;
    private Date create_time;
}
