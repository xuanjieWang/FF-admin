package org.dromara.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.domain.AOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AOrder.class, reverseConvertGenerate = false)
public class AAccountBo extends BaseEntity {

    private Long id;

    private String sjsPhone;
    private String sjsName;
    private String orderType;


    private Long orderId;

    private String orderTitle;

    private String kf;

    private String wangwang;

    private BigDecimal money;

    // 余额
    private BigDecimal balance;

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

    // 部门名称
    private String deptName;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
