package cn.beardestiny.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 15:22
 * @Sign “江湖夜雨十年灯”
 * @description: 表白墙帖子实体类
 */
@Data
@TableName("gossip_post")
public class GossipPost implements Serializable {
    public static final Long serialVersionPID = 1L;

    @TableId( type = IdType.AUTO )
    private Long id;

    private String post_id;
    private String user_id;
    private String post_text;
    private Long post_like;
    private Date create_time;
    private Date modify_time;
}
