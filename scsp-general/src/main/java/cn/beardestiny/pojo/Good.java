package cn.beardestiny.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 3:05
 * @Sign “江湖夜雨十年灯”
 * @description: 商品实体类
 */

@Data
@TableName("good")
public class Good {
    public static final Long serialVersionPID = 1L;

    @TableId( type = IdType.AUTO )
    private Long id;
    private String good_id;
    @NotBlank
    private String seller_id;
    @NotBlank
    private String good_name;
    @NotBlank
    private String category_id;
    @NotBlank
    private String cover_img;

    @NotNull
    private Double good_price;

    @NotNull
    private Integer good_number;

    @NotBlank
    private String good_detail;

    private Integer good_status;
    private Date create_time;
    private Date modify_time;
}
