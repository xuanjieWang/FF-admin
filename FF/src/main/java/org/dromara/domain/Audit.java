package org.dromara.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@TableName("a_audit")
public class Audit {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String qq;

    private String qqMail;

    private String zfb;

    private String name;

    private String work;

    private String proficient;

    private String sfz;

    private String sfzImg;

    private String phonenumber;

    // 邀请人
    private String invitePeople;

    private  String designerType;


    @TableField(exist = false)
    private String audit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date AuditTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date DelTime;

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
