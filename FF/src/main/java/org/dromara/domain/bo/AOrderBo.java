package org.dromara.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.domain.AOrder;

import java.math.BigDecimal;
import java.util.Date;

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

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;


    /**
     * 订单标题
     */
    @NotNull(message = "订单标题", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date creatTime;

    /**
     * 发布人
     */
    @NotBlank(message = "发布人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pushUser;

    /**
     * 接收人
     */
    @NotBlank(message = "接收人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String work;

    /**
     * 发布信息
     */
    @NotBlank(message = "发布信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String text;

    /**
     * 发布金额
     */
    @NotNull(message = "发布金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal money;

    /**
     * 限制时间
     */
    @NotNull(message = "限制时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date linitTime;

    /**
     * 开发语言
     */
    @NotBlank(message = "开发语言不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 是否需要论文
     */
    @NotBlank(message = "是否需要论文不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isPaper;

    /**
     * 更新人
     */
    @NotBlank(message = "修改人", groups = { EditGroup.class })
    private String updateUser;


    /**
     * 发布状态
     */
    @NotBlank(message = "发布状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String isRelease;


}
