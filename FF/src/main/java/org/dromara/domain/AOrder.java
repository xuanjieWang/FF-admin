package org.dromara.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 【请填写功能名称】对象 a_order
 *
 * @author Lion Li
 * @date 2024-04-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("a_order")
public class AOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 订单标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 发布人
     */
    private String pushUser;

    /**
     * 接收人
     */
    private String work;

    /**
     * 发布信息
     */
    private String text;

    /**
     * 发布金额
     */
    private BigDecimal money;

    /**
     * 限制时间
     */
    private Date linitTime;

    /**
     * 开发语言
     */
    private String type;

    /**
     * 是否需要论文
     */
    private String isPaper;

    /**
     * 发布状态
     */
    private String isRelease;

    private String updateUser;

    /**
     * 2代表删除
     */
    @TableLogic
    private String delFlag;


}
