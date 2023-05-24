package cn.beardestiny.param;

import lombok.Data;

/**
 * @Author BearDestiny
 * @Date 2023/4/27 19:22
 * @Sign “江湖夜雨十年灯”
 * @description: 前端商品item类
 */
@Data
public class GoodItem {
    private String good_id;
    private String cover_img;
    private String good_name;
    private Double good_price;
    private String collect_num;
}
