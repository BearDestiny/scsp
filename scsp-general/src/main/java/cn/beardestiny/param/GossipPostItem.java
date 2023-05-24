package cn.beardestiny.param;

import cn.beardestiny.pojo.GossipPostComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/17 20:30
 * @Sign “江湖夜雨十年灯”
 * @description: 前端校园墙帖子列表单体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GossipPostItem {
    private String post_id;
    private String user_nickname;
    private String user_headImg;
    private Date create_time;
    private String post_text;
    private Long post_like;
    private List<String> post_img;
    private List<GossipPostComment> post_comment;
}
