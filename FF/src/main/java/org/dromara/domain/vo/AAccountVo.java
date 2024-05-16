package org.dromara.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.domain.AAccount;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AAccount.class)
public class AAccountVo {
    @Serial
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "结算编号")
    private Long id;

    @ExcelProperty(value = "设计师账号")
    private String sjsPhone;
    @ExcelProperty(value = "设计师姓名")
    private String sjsName;

    @ExcelProperty(value = "订单编号")
    private Long orderId;

    @ExcelProperty(value = "订单类型")
    private String orderType;

    @ExcelProperty(value = "订单标题")
    private String orderTitle;

    @ExcelProperty(value = "对接客服")
    private String kf;

    @ExcelProperty(value = "旺旺号")
    private String wangwang;

    @ExcelProperty(value = "提成金额")
    private BigDecimal money;

    @ExcelProperty(value = "余额")
    private BigDecimal balance;


    @ExcelProperty(value = "结算状态")
    private String jsStatus;

    @TableLogic
    @ExcelProperty(value = "2代表删除")
    private String delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
