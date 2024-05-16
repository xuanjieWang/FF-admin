package org.dromara.service;


import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.AOrder;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AOrderVo;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lion Li
 * @date 2024-04-16
 */
public interface IAOrderService {

    /**
     * 查询【请填写功能名称】
     */
    AOrderVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<AOrderVo> queryPageList(AOrderBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<AOrderVo> queryList(AOrderBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(AOrder order);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(AOrderBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    TableDataInfo<AOrderVo> listHis(AOrderBo bo, PageQuery pageQuery);

    int subcomm(AOrderBo bo);
}
