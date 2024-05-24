package org.dromara.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 【请填写功能名称】对象 a_order
 *
 * @author Lion Li
 * @date 2024-04-16
 */
@Data
@TableName("a_order")
public class AOrder {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String title;

    // 客服
    private String kf;

    // 旺旺号
    private String wangwang;

    // 提成金额
    private BigDecimal money;

    // 订单状态
    private String orderStatus;

    // 结算状态
    private String jsStatus;

    // 提现状态
    private String txStatus;

    // 设计师账号
    private String sjsPhone;

    // 设计师姓名
    private String sjsName;

    //下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xdTime;

    // 交付时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jfTime;

    /**
     * 2代表删除
     */
    @TableLogic
    private String delFlag;

    private String type;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();


}
