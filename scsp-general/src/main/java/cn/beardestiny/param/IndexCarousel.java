package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 3:48
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexCarousel {
    private String sender_name;
    private String reciver_name;
    private String theme;
    private String carousel_img;
}
