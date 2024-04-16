package org.dromara.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.domain.AOrder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



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

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date creatTime;

    /**
     * 发布人
     */
    @ExcelProperty(value = "发布人")
    private String pushUser;

    /**
     * 接收人
     */
    @ExcelProperty(value = "接收人")
    private String work;

    /**
     * 发布信息
     */
    @ExcelProperty(value = "发布信息")
    private String text;

    /**
     * 发布金额
     */
    @ExcelProperty(value = "发布金额")
    private BigDecimal money;

    /**
     * 限制时间
     */
    @ExcelProperty(value = "限制时间")
    private Date linitTime;

    /**
     * 开发语言
     */
    @ExcelProperty(value = "开发语言")
    private String type;

    /**
     * 是否需要论文
     */
    @ExcelProperty(value = "是否需要论文")
    private String isPaper;

    /**
     * 发布状态
     */
    @ExcelProperty(value = "发布状态")
    private String isRelease;


}
