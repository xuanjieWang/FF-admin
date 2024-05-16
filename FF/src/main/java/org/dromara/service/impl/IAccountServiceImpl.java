package org.dromara.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.AAccount;
import org.dromara.domain.bo.AAccountBo;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AAccountVo;
import org.dromara.mapper.AAccountMapper;
import org.dromara.service.IAccountService;
import org.dromara.system.domain.SysUser;
import org.dromara.system.service.ISysUserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IAccountServiceImpl implements IAccountService {
    private final AAccountMapper baseMapper;
    private final ISysUserService sysUserService;

    @Override
    public TableDataInfo<AAccountVo> queryPageList(AAccountBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AAccount> lqw = new LambdaQueryWrapper<>();

        // 设计师只能看到自己的，客服只能看到自己的
        if (org.dromara.common.core.utils.StringUtils.isNotBlank(bo.getDeptName())) {
            if ("设计师部门".equals(bo.getDeptName())) {
                lqw.eq(AAccount::getSjsPhone, bo.getSjsPhone());
            } else if ("客服部门".equals(bo.getDeptName())) {
                lqw.eq(AAccount::getKf, bo.getSjsPhone());
            }
        }
        lqw.orderByDesc(AAccount::getCreateTime);
        IPage<AAccountVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    // 项目结算成功的时候添加信息
    @Override
    public boolean addAccount(AOrderBo bo) {

        AAccount aAccount = new AAccount();
        aAccount.setOrderTitle(bo.getTitle());
        aAccount.setOrderId(bo.getId());
        aAccount.setKf(bo.getKf());
        aAccount.setJsStatus(bo.getJsStatus());
        aAccount.setSjsPhone(bo.getSjsPhone());
        aAccount.setSjsName(bo.getSjsName());
        aAccount.setWangwang(bo.getWangwang());
        aAccount.setMoney(bo.getMoney()); //交易金额
        aAccount.setCreateTime(DateUtils.getNowDate());
        aAccount.setDelFlag("0");
        aAccount.setOrderType(bo.getType());

        SysUser sjs = sysUserService.selectUserByPhonenumber(bo.getSjsPhone());
        if(null == sjs) {
            return false;
        }
        // 设置余额, 先设置订单然后设置用户
        if(null == sjs.getMoney()){
            aAccount.setBalance(bo.getMoney());
            sjs.setMoney(bo.getMoney());
        }else{
            aAccount.setBalance(bo.getMoney().add(sjs.getMoney()));
            sjs.setMoney(bo.getMoney().add(sjs.getMoney()));
        }
        sysUserService.updateUser1(sjs);
        baseMapper.insert(aAccount);
        return true;
    }


    // 扣除金额
    @Override
    public int DedAccount(AOrderBo bo) {
        AAccount aAccount = new AAccount();
        aAccount.setOrderTitle(bo.getTitle()); //扣除金额标题
        aAccount.setMoney(bo.getMoney()); //交易金额
        aAccount.setOrderType(bo.getType()); //扣除金额理由

        aAccount.setCreateTime(DateUtils.getNowDate());
        aAccount.setDelFlag("0");

        SysUser sjs = sysUserService.selectUserByPhonenumber(bo.getSjsPhone());
        if(null == sjs) return -1;

        // 设置余额, 先设置订单然后设置用户
        if(null == sjs.getMoney()){
            aAccount.setBalance(bo.getMoney().negate());
            sjs.setMoney(bo.getMoney().negate());
        }else{
            // 账户余额是设计师的钱减去-扣除的钱
            aAccount.setBalance(sjs.getMoney().subtract(bo.getMoney()));
            sjs.setMoney(sjs.getMoney().subtract(bo.getMoney()));
        }
        sysUserService.updateUser1(sjs);

        return baseMapper.insert(aAccount);
    }
}
