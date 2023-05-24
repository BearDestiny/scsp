package cn.beardestiny.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/1 6:51
 * @Sign “江湖夜雨十年灯”
 * @description: 订单实体类
 */
@Data
@TableName("good_order")
public class GoodOrder implements Serializable {
    public static final Long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;
    private String order_id;
    private String good_id;
    private Integer buy_num;
    private String buyer_id;
    private Integer order_status;
    private Date create_time;
    private Date modify_time;
}
