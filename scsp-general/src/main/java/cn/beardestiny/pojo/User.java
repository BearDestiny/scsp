package cn.beardestiny.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Title: User
 * @Author BearDestiny
 * @Package cn.beardestiny.pojo
 * @Date 2023/4/7 14:31
 * @description: 用户实体类
 */

@Data
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    public static final Long serialVersionUID = 1L;

    @TableId( type = IdType.AUTO )
    private Long id;
    private String user_id;
    private String user_account;
    private String user_password;
    private String user_phone;
    private String user_sno;
    private Long user_currency;
    private String user_nickname;
    @TableField("user_headImg")
    private String user_headImg;
    private Date create_time;
    private Date modify_time;
}
