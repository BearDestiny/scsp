package cn.beardestiny.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/22 20:27
 * @Sign “江湖夜雨十年灯”
 * @description: 用户前端可显示参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontUser {
    private String user_id;
    private String user_account;
    private String user_nickname;
    private String user_headImg;
    private String achi_using;
    private List<String> pubname_value;
    private Long user_currency;

    public FrontUser(String user_id, String user_account, String user_nickname, String user_headImg) {
        this.user_id = user_id;
        this.user_account = user_account;
        this.user_nickname = user_nickname;
        this.user_headImg = user_headImg;
    }

}
