package cn.beardestiny.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: 返回结果通用对象  Map
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RCode {

    /**
     * 通用失败状态码
     */
    public static final String FAIL_CODE = "000";

    /**
     * 通用成功状态码
     */
    public static final String SUCCESS_CODE = "001";

    /**
     * 规则不通过状态码
     */
    public static final String RULE_REJECT_CODE = "002";

    /**
     * 功能性故障状态码
     */
    public static final String FUN_FAULT_CODE = "003";

    /**
     * 系统性故障状态码
     */
    public static final String SYS_FAULT_CODE = "004";


    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long   total;


    /**
     * 通用成功
     * @param msg   返回成功信息
     * @param data  返回成功数据
     * @param total  返回成功总数
     * @return RCode
     */
    public static RCode pass(String msg, Object data, Long total){
        return new RCode(SUCCESS_CODE, msg, data, total);
    }

    public static RCode pass(String msg, Object data){
        return pass(msg,data,null);
    }

    public static RCode pass(String msg){
        return pass(msg,null);
    }

    public static RCode pass(Object data){
        return pass(null,data);
    }


    /**
     * 通用失败
     * @param msg   返回成功信息
     * @param data  返回成功数据
     * @param total  返回成功总数
     * @return RCode
     */
    public static RCode failure(String msg, Object data, Long total){
        return new RCode(FAIL_CODE,msg,data,total);
    }

    public static RCode failure(String msg, Object data){
        return failure(msg,data,null);
    }

    public static RCode failure(String msg){
        return failure(msg,null);
    }

    public static RCode failure(Object data){
        return failure(null,data);
    }

    /**
     * 规则不允许
     */

    public static RCode ruleReject(String msg, Object data, Long total){
        return new RCode(RULE_REJECT_CODE, msg, data, total);
    }

    public static RCode ruleReject(String msg, Object data){
        return ruleReject(msg, data, null);
    }

    public static RCode ruleReject(String msg){
        return ruleReject(msg, null);
    }

    public static RCode ruleReject(Object data){
        return ruleReject(null, data);
    }


}
