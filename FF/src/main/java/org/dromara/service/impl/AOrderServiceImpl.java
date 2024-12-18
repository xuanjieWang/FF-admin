package org.dromara.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.AOrder;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AOrderVo;
import org.dromara.mapper.AOrderMapper;
import org.dromara.service.IAOrderService;
import org.dromara.service.IAccountService;
import org.dromara.system.domain.SysUser;
import org.dromara.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private final IAccountService countsService;


    /**
     * 查询【请填写功能名称】
     */
    @Override
    public AOrderVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<AOrderVo> queryPageList(AOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AOrder> lqw = new LambdaQueryWrapper<>();
        // 订单状态
//        lqw.eq(StringUtils.isNotBlank(bo.getOrderStatus()), AOrder::getOrderStatus, bo.getOrderStatus());
//        // 提成状态
//        lqw.eq(StringUtils.isNotBlank(bo.getJsStatus()), AOrder::getJsStatus, bo.getJsStatus());
        // 提成类型
//        lqw.eq(StringUtils.isNotBlank(bo.getCommonType()), AOrder::getCommonType, bo.getCommonType());

        // 设计师姓名
        lqw.like(StringUtils.isNotBlank(bo.getSjsName()), AOrder::getSjsName, bo.getSjsName());
        // 订单标题
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), AOrder::getTitle, bo.getTitle());
        //订单类型
        lqw.like(StringUtils.isNotBlank(bo.getType()), AOrder::getType, bo.getType());

        lqw.eq(AOrder::getOrderStatus,"交易中");
        if (StringUtils.isNotBlank(bo.getDeptName())) {
            if ("设计师部门".equals(bo.getDeptName())) {
                lqw.eq(AOrder::getSjsPhone, bo.getSjsPhone());
            } else if ("客服部门".equals(bo.getDeptName())) {
                lqw.eq(AOrder::getKf, bo.getSjsPhone());
            }
        }

        lqw.orderByDesc(AOrder::getCreateTime);
        Page<AOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<AOrderVo> queryList(AOrderBo bo) {
        LambdaQueryWrapper<AOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(AOrder::getJsStatus,"未结算");
        lqw.eq(AOrder::getOrderStatus, "交易中");
        lqw.eq(AOrder::getDelFlag,0);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(AOrder order) {
        validEntityBeforeSave(order);
        order.setCreateTime(DateUtils.getNowDate());
        order.setDelFlag("0");

        // 添加默认值
        order.setOrderStatus("交易中");
        order.setJsStatus("未结算");
//        order.setTxStatus("未提现");

        boolean flag = baseMapper.insert(order) > 0;
        return flag;
    }

    private final ISysUserService sysUserService;


    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean updateByBo(AOrderBo bo) {
        AOrder update = MapstructUtils.convert(bo, AOrder.class);
        validEntityBeforeSave(update);

        // 交易成功，直接去调用操作余额表
        if(StringUtils.isNotBlank(bo.getOrderStatus() )&& "交易完成".equals(bo.getOrderStatus())){

            // 判断设计师是否存在
            SysUser sjs = sysUserService.selectUserByPhonenumber(bo.getSjsPhone());
            if(null == sjs) {
                return false;
            }
            update.setOrderStatus("交易完成");
            update.setJsStatus("订单核验中");

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                if (!countsService.addAccount(bo)) {
                    update.setJsStatus("订单失败");
                    update.setTxStatus("订单失败");
                } else {
                    update.setJsStatus("已结算");
                    update.setTxStatus("待提现");
                }
                baseMapper.updateById(update);
            }, 7, TimeUnit.DAYS);
//        }, 5, TimeUnit.MINUTES);
            executorService.shutdown();
        }

        update.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AOrder entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除【请填写功能名称】
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public TableDataInfo<AOrderVo> listHis(AOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AOrder> lqw = new LambdaQueryWrapper<>();
        // 设计师姓名
        lqw.like(StringUtils.isNotBlank(bo.getSjsName()), AOrder::getSjsName, bo.getSjsName());
        // 订单标题
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), AOrder::getTitle, bo.getTitle());
        //订单类型
        lqw.like(StringUtils.isNotBlank(bo.getType()), AOrder::getType, bo.getType());

        if (StringUtils.isNotBlank(bo.getDeptName())) {
            if ("设计师部门".equals(bo.getDeptName())) {
                lqw.eq(AOrder::getSjsPhone, bo.getSjsPhone());
            } else if ("客服部门".equals(bo.getDeptName())) {
                lqw.eq(AOrder::getKf, bo.getSjsPhone());
            }
        }

        lqw.orderByDesc(AOrder::getUpdateTime);
        lqw.ne(AOrder::getOrderStatus,"交易中");
        Page<AOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public int subcomm(AOrderBo bo) {
        AOrder update = MapstructUtils.convert(bo, AOrder.class);
        return baseMapper.updateById(update);
    }

    // 获取到所有核验的订单
    @Override
    public List<AOrder> getHYOrder() {
        LambdaQueryWrapper<AOrder> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AOrder::getJsStatus,"订单核验中");
        lqw.lt(AOrder::getUpdateTime, DateUtils.addDays(new Date(), -7));
        return baseMapper.selectList(lqw);
    }

    @Override
    public void updateById(AOrder item) {
        baseMapper.updateById(item);
    }


    /**
     * 获取提现订单
     * 1. 设计师名称
     * 2. 审核的时候 订单创建时间一定要小于提现的时间
     * **/
    @Override
    public List<AOrder> getTxOrder(AOrderVo orderVo) {
        LambdaQueryWrapper<AOrder> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AOrder::getTxStatus,"待提现");
        lqw.eq(AOrder::getSjsPhone,orderVo.getSjsPhone());
        lqw.lt(null != orderVo.getCreateTime(), AOrder::getCreateTime, orderVo.getCreateTime());

        lqw.orderByDesc(AOrder::getCreateTime);
        return baseMapper.selectList(lqw);
    }

    @Override
    public void updateBatchById(List<AOrder> txOrder) {
        baseMapper.updateBatchById(txOrder);
    }
}
