package cn.beardestiny.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/2 4:17
 * @Sign “江湖夜雨十年灯”
 * @description: 交易留言实体类
 */

@Data
@AllArgsConstructor
@TableName("talk")
public class Talk {

    private Long id;
    private String speaker_id;
    private String listener_id;
    private String message;
    private Date create_time;
    private Date modify_time;

}
