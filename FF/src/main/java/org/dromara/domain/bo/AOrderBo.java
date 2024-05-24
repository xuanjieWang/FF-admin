package org.dromara.domain.bo;

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

/**
 * 【请填写功能名称】业务对象 a_order
 *
 * @author Lion Li
 * @date 2024-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AOrder.class, reverseConvertGenerate = false)
public class AOrderBo extends BaseEntity {

    private Long id;

    private String title;

    // 客服
    private String kf;

    // 旺旺号
    private String wangwang;

    // 提成金额
    private BigDecimal money;

    private BigDecimal ye;

    // 订单状态
    private String orderStatus;


    private String type;
    // 结算状态
    private String jsStatus;

    // 提现状态
    private String txStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date xdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jfTime;

    /**
     * 2代表删除
     */
    private String delFlag;

    private String userId;


    // 设计师账号
    private String sjsPhone;

    // 设计师姓名
    private String sjsName;
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
    private Map<String, Object> params = new HashMap<>();


    private String deptName;

}
