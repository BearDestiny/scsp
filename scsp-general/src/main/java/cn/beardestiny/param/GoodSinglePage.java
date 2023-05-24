package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/30 19:47
 * @Sign “江湖夜雨十年灯”
 * @description: 商品详情页参数类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodSinglePage {

    private String good_id;
    private String seller_id;
    private String user_nickname;
    private String good_name;
    private String category_name;
    private String cover_img;
    private Double good_price;
    private Integer good_number;
    private String good_detail;
    private List<String> detail_img;
    private Date create_time;
}
