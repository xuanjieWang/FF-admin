package org.dromara.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.domain.Audit;
import org.dromara.mapper.AAuditMapper;
import org.dromara.service.IAuditService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IAuditServiceImpl implements IAuditService {

    private final AAuditMapper baseMapper;

    @Override
    public void addRealInfo(Audit user) {
        baseMapper.insert(user);
    }

    @Override
    public Audit selectByUserId(Long userId) {
        LambdaQueryWrapper<Audit> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Audit::getUserId,userId);
        lqw.eq(Audit::getDelFlag,"0");
        return baseMapper.selectOne(lqw);
    }

    @Override
    public void updateById(Audit audit) {
        baseMapper.updateById(audit);
    }

    @Override
    public List<Audit> getListByUserName(String InvitePeople) {
        LambdaQueryWrapper<Audit> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(Audit::getInvitePeople,InvitePeople);
        lqw.orderByDesc(Audit::getCreateTime);
        return baseMapper.selectList(lqw);
    }

    @Override
    public void deleteById(Long id) {
        // 添加操作时间
        Audit audit = new Audit();
        audit.setAuditTime(DateUtils.getNowDate());
        audit.setId(id);
        baseMapper.updateById(audit);
        baseMapper.deleteById(id);
    }
}
