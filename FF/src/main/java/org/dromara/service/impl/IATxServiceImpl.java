package org.dromara.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.AOrder;
import org.dromara.domain.ATx;
import org.dromara.domain.bo.ATxBo;
import org.dromara.domain.vo.AOrderVo;
import org.dromara.domain.vo.ATxVo;
import org.dromara.mapper.AATxMapper;
import org.dromara.service.IAOrderService;
import org.dromara.service.IATxService;
import org.dromara.system.domain.SysUser;
import org.dromara.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IATxServiceImpl implements IATxService {
    private final AATxMapper baseMapper;

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IAOrderService orderService;


    @Override
    public TableDataInfo<ATxVo> queryPageList(ATxBo bo, PageQuery pageQuery) {
        // 查询出提现记录，也查询出提现申请
        LambdaQueryWrapper<ATx> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotBlank(bo.getSjsPhone()), ATx::getSjsPhone, bo.getSjsPhone());
        lqw.orderByDesc(ATx::getCreateTime);
        lqw.isNull(ATx::getMessage);
        lqw.eq(ATx::getDelFlag, "0");
        IPage<ATxVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public TableDataInfo<ATxVo> queryPageListDis(ATxBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ATx> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.isNotBlank(bo.getSjsPhone()), ATx::getSjsPhone, bo.getSjsPhone());
        lqw.orderByDesc(ATx::getCreateTime);
        lqw.eq(ATx::getDelFlag, "0");
        IPage<ATxVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    // 审核
    @Override
    public R<Void> adopt(ATx bo) {
        SysUser user = sysUserService.selectUserByPhonenumber(bo.getSjsPhone());
        if (null == user.getMoney() || null == bo.getMoney()) return R.fail("账户余额错误");

        // 查询之前的审核状态
        ATx tx = baseMapper.selectById(bo.getId());
        if (null != tx.getSuccessFlag()) {
            return R.fail("该提现申请已经审核，不可重复申请");
        }

        AOrderVo vo = new AOrderVo();
        vo.setSjsPhone(bo.getSjsPhone());
        vo.setCreateTime(tx.getCreateTime());

        List<AOrder> txOrder = orderService.getTxOrder(vo);

        // 提现失败需要退还金额
        if ("1".equals(bo.getSuccessFlag())) {
            user.setMoney(user.getMoney().add(bo.getMoney()));
            sysUserService.updateUser1(user);
            bo.setBalance(user.getMoney());
            txOrder.forEach(item -> {
                item.setTxStatus("订单取消");
            });
        } else {
            txOrder.forEach(item -> {
                item.setTxStatus("已提现");
            });
        }

        bo.setTxTime(DateUtils.getNowDate());
        baseMapper.updateById(bo);
        orderService.updateBatchById(txOrder);

        return R.ok();
    }

    /**
     * 1. 申请体现，将体现金额扣除
     * 2. 设置提现时间
     * **/
    @Override
    public R<Void> setTx(ATx bo) {
        SysUser user = sysUserService.selectUserByPhonenumber(bo.getSjsPhone());
        if (null == user) return R.fail("当前用户不能进行操作");
        if (null == user.getMoney() || null == bo.getMoney()) return R.fail("账户余额错误");

        // 提现金额不足
        if (0 > user.getMoney().compareTo(bo.getMoney())) {
            return R.fail("操作金额不足");
        }

        // 设置用户余额
        user.setMoney(user.getMoney().subtract(bo.getMoney()));
        sysUserService.updateUser1(user);

        // 提现申请将余额扣除
        bo.setZfb(user.getZfb());
        bo.setBalance(user.getMoney());
        bo.setCreateTime(DateUtils.getNowDate());
        bo.setDelFlag("0");

        // 如果有提现原因就是扣款
        if(StringUtils.isNotBlank(bo.getMessage())){
            bo.setTxTime(DateUtils.getNowDate());
        }
        baseMapper.insert(bo);
        return R.ok();
    }


    /**
     * 查询出用户的扣款订单
     * 查询出扣款订单是在
     * **/
    @Override
    public R getDisOrderList(ATx bo) {
        LambdaQueryWrapper<ATx> lqw = new LambdaQueryWrapper<>();
        lqw.isNotNull(ATx::getMessage);
        lqw.eq(ATx::getDelFlag, "0");
        lqw.eq(ATx::getSuccessFlag, "3");
        return R.ok(baseMapper.selectList(lqw));
    }
}
