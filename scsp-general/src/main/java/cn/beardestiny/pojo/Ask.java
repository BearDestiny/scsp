package cn.beardestiny.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/4/30 17:29
 * @Sign “江湖夜雨十年灯”
 * @description: 问答帖子
 */

@Data
public class Ask {
    public static final Long serialVersionPID = 1L;

    @TableId( type = IdType.AUTO )
    private Long id;
    private String ask_id;
    private String ask_title;
    private String subject_category;
    private Integer bonus;
    private String user_id;
    private String ask_content;
    private String anwser_id;
    private Date create_time;
    private Date modify_time;
}
