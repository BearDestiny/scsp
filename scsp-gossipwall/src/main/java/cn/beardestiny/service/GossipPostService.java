package cn.beardestiny.service;

import cn.beardestiny.param.GossipPostItem;
import cn.beardestiny.param.PostCommentParam;
import cn.beardestiny.pojo.GossipPost;
import cn.beardestiny.pojo.VipPost;
import cn.beardestiny.utils.RCode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 18:48
 * @Sign “江湖夜雨十年灯”
 * @description: 校园墙帖子服务类
 */
public interface GossipPostService {

    /**
     * 发布帖子
     */
    RCode insertGossipPost(GossipPost gossipPost);


    /**
     * 发布Vip帖子
     */
    RCode insertVipPost(VipPost vipPost);


    /**
     * 帖子分页
     * @param pageNum 页数
     */
    RCode selectGossipPostsByPage(int pageNum);


    /**
     * 数量获取最新帖子
     */
    RCode selectGossipPostsByNum(int num);


    /**
     * 获取模糊搜索帖子
     */
    RCode selectLikeSearchPost(String keyword);


    /**
     * post_id获取帖子
     */
    RCode selectGossipPostById(String post_id);


    /**
     * 帖子图片持久化
     */
    RCode insertGossipPostImgs(List<String> imgList);


    /**
     * 帖子点赞
     */
    RCode insertGossipPostLike(String post_id, String user_id);


    /**
     * 帖子取消点赞
     */
    RCode deleteGossipPostLike(String post_id, String user_id);


    /**
     * 用户点赞查询
     */
    RCode selectGossipPostLike(String user_id);


    /**
     * 帖子新增评论
     */
    RCode addPostComment(PostCommentParam param);
}
