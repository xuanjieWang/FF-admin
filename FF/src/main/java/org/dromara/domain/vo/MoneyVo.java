package org.dromara.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;

import java.io.Serial;

@Data
public class MoneyVo {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer beginTime;

    private Integer endTime;

    // 时间，周，月
    private String type;

}
