package org.dromara.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.domain.AOrder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 【请填写功能名称】视图对象 a_order
 *
 * @author Lion Li
 * @date 2024-04-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AOrder.class)
public class AOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "订单编号")
    private Long id;

    @ExcelProperty(value = "订单标题")
    private String title;

    @ExcelProperty(value = "对接客服")
    private String kf;

    @ExcelProperty(value = "客服旺旺号")
    private String wangwang;

    @ExcelProperty(value = "提成金额")
    private BigDecimal money;

    @ExcelProperty(value = "订单状态")
    private String orderStatus;

    @ExcelProperty(value = "结算状态")
    private String jsStatus;

    @ExcelProperty(value = "订单评价")
    private String common;

    @ExcelProperty(value = "订单类型")
    private String type;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "下单时间")
    private Date xdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "交付时间")
    private Date jfTime;

    private String delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "订单更新时间")
    private Date updateTime;


    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params = new HashMap<>();

}
