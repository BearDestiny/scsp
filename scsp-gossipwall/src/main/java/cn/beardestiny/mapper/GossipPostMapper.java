package cn.beardestiny.mapper;

import cn.beardestiny.param.GossipPostItem;
import cn.beardestiny.param.PostCommentParam;
import cn.beardestiny.pojo.GossipPost;
import cn.beardestiny.pojo.VipPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 18:45
 * @Sign “江湖夜雨十年灯”
 * @description: 表白墙帖子mapper
 */
@Repository
public interface GossipPostMapper extends BaseMapper<GossipPost> {

    /**
     * 根据页面获取帖子
     */
    List<GossipPostItem> selectGossipPostItemsByPage(int pageNum);

    /**
     * 根据数量获取帖子
     */
    List<GossipPostItem> selectGossipPostItemsByNum(int num);

    /**
     * 根据id获取帖子
     */
    GossipPostItem selectGossipPostById(String post_id);

    /**
     * 帖子模糊查询
     */
    List<GossipPostItem> selectGossipPostByLikeWord(String keyWord);

    /**
     * 根据点赞信息，增加点赞记录
     */
    @Insert("insert into `gossippost_like`(post_id, user_id) values(#{post_id}, #{user_id})")
    int insertGossipPostLike(String post_id, String user_id);

    /**
     *  删除点赞记录
     */
    @Delete("delete from `gossippost_like` where post_id = #{post_id} and user_id = #{user_id}")
    int deleteGossipPostLike(String post_id, String user_id);

    /**
     * 帖子点赞总数增1
     */
    @Update("update `gossip_post` set post_like = post_like + 1 where post_id = #{arg0}")
    int incrGossipPostLike(String post_id);

    /**
     * 帖子点赞总数减1
     */
    @Update("update `gossip_post` set post_like = post_like - 1 where post_id = #{arg0}")
    int decrGossipPostLike(String post_id);

    /**
     * 判断用户点赞了哪些帖子
     */
    @Select("select post_id from `gossippost_like` where user_id = #{arg0}")
    List<String> selectPostIdByUserLike(String user_id);


    /**
     * 新增帖子评论
     */
    @Insert("insert into `gossippost_comment`(post_id, user_id, comment_content) values(#{comment.post_id}, #{comment.user_id}, #{comment.comment_content})")
    Integer insertPostComment(@Param("comment") PostCommentParam param);


    /**
     * 新增vip帖子
     */
    @Insert("insert into `gossip_vip_post`(post_id, sender_id, reciver_name, post_img, post_theme) values(#{p.post_id}, #{p.sender_id}, #{p.reciver_name}, #{p.post_img}, #{p.post_theme})")
    Integer insertVipPost(@Param("p")VipPost post);

}
