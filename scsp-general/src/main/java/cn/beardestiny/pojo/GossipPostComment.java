package cn.beardestiny.pojo;

import lombok.Data;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/4/18 10:38
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子评论实体类
 */
@Data
public class GossipPostComment {
    private String user_id;
    private String user_nickname;
    private String comment_content;
    private Date comment_time;
}
