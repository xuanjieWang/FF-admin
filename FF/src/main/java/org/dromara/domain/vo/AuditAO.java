package org.dromara.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.domain.Audit;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@AutoMapper(target = Audit.class)
public class AuditAO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    private String userId;

    private String qq;

    private String qqMail;

    private String zfb;

    private String name;

    private String work;

    private String proficient;

    private String sfz;

    private String sfzImg;

    private String phonenumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creatTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 2代表删除
     */
    @TableLogic
    private String delFlag;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();


}
