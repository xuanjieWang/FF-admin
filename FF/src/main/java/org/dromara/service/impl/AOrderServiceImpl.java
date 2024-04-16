package org.dromara.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.AOrder;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AOrderVo;
import org.dromara.mapper.AOrderMapper;
import org.dromara.service.IAOrderService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lion Li
 * @date 2024-04-16
 */
@RequiredArgsConstructor
@Service
public class AOrderServiceImpl implements IAOrderService {

    private final AOrderMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public AOrderVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<AOrderVo> queryPageList(AOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AOrder> lqw = buildQueryWrapper(bo);
        Page<AOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<AOrderVo> queryList(AOrderBo bo) {
        LambdaQueryWrapper<AOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AOrder> buildQueryWrapper(AOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCreatTime() != null, AOrder::getCreatTime, bo.getCreatTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPushUser()), AOrder::getPushUser, bo.getPushUser());
        lqw.eq(StringUtils.isNotBlank(bo.getWork()), AOrder::getWork, bo.getWork());
        lqw.eq(StringUtils.isNotBlank(bo.getText()), AOrder::getText, bo.getText());
        lqw.eq(bo.getMoney() != null, AOrder::getMoney, bo.getMoney());
        lqw.eq(bo.getLinitTime() != null, AOrder::getLinitTime, bo.getLinitTime());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), AOrder::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getIsPaper()), AOrder::getIsPaper, bo.getIsPaper());
        lqw.eq(StringUtils.isNotBlank(bo.getIsRelease()), AOrder::getIsRelease, bo.getIsRelease());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(AOrderBo bo) {
        AOrder add = MapstructUtils.convert(bo, AOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean updateByBo(AOrderBo bo) {
        AOrder update = MapstructUtils.convert(bo, AOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除【请填写功能名称】
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
