package cn.beardestiny.param;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 4:05
 * @Sign “江湖夜雨十年灯”
 * @description: 前端新增商品参数类
 */

@Data
public class NewGoodParam {
    @NotNull(message = "good_name，不能为空")
    private String good_name;
    @NotNull(message = "category_id，不能为空")
    private String category_id;

    private MultipartFile cover_img;

    @NotNull(message = "good_price，不能为空")
    private String good_price;
    @NotNull(message = "good_num，不能为空")
    private String good_num;
    @NotNull(message = "good_detail，不能为空")
    private String good_detail;

    private MultipartFile[] good_imgList;

    @NotNull(message = "submit_phone，不能为空")
    private String submit_phone;
    @NotNull(message = "submit_verifyCode，不能为空")
    private String submit_verifyCode;

}
