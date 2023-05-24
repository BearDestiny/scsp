package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author BearDestiny
 * @Date 2023/5/5 19:25
 * @Sign “江湖夜雨十年灯”
 * @description:
 */

@Data
@AllArgsConstructor
public class BuyParam {

    private String good_id;
    private String buyer_id;
    private Integer buy_num;

}
