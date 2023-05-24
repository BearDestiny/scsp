package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author BearDestiny
 * @Date 2023/5/6 7:25
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Data
@AllArgsConstructor
public class PostCommentParam {
    private String post_id;
    private String user_id;
    private String comment_content;
}
