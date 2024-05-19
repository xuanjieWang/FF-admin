package org.dromara.service;

import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.ATx;
import org.dromara.domain.bo.ATxBo;
import org.dromara.domain.vo.ATxVo;

public interface IATxService {
    TableDataInfo<ATxVo> queryPageList(ATxBo bo, PageQuery pageQuery);

    R<Void> adopt(ATx bo);

    R<Void> setTx(ATx bo);

    TableDataInfo<ATxVo> queryPageListDis(ATxBo bo, PageQuery pageQuery);
}
