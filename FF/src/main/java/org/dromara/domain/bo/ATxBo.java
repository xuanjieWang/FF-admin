package org.dromara.domain.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.domain.ATx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ATx.class, reverseConvertGenerate = false)
public class ATxBo extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String sjsPhone;
    private String sjsName;
    private String zfb;
    private BigDecimal money;
    private BigDecimal balance;
    private String successFlag;

    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date txTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


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
