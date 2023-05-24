package cn.beardestiny.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author BearDestiny
 * @Date 2023/5/14 12:13
 * @Sign “江湖夜雨十年灯”
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkParam {
    private Long id;
    private String speaker_name;
    private String user_headImg;
    private String message;
    private Date create_time;
}
