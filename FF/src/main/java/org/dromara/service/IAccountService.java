package org.dromara.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.AOrder;
import org.dromara.domain.bo.AAccountBo;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AAccountVo;

public interface IAccountService {
    TableDataInfo<AAccountVo> queryPageList(AAccountBo bo, PageQuery pageQuery);

    boolean addAccount(AOrderBo bo);
    boolean addAccount(AOrder bo);

    int DedAccount(AOrderBo bo);
}
