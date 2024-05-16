package org.dromara.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.web.core.BaseController;
import org.dromara.domain.Audit;
import org.dromara.service.IAuditService;
import org.dromara.system.domain.SysUser;
import org.dromara.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Audit/data")
public class AuditController extends BaseController {

    @Autowired private ISysUserService userService;

    @Autowired private IAuditService auditService;


    // 添加用户的实名信息, 将状态修改为待审核，将
    @PostMapping("/addRealInfo")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> addRealInfo(@RequestBody Audit user) {
        SysUser sysUser = userService.selectById(user.getUserId());
        if (null == sysUser || !"注册中".equals(sysUser.getRegisStatus())) {
            return R.fail("用户信息已经在审核中！");
        }

        // 更新状态
        sysUser.setRegisStatus("审核中");
        userService.updateUser1(sysUser);

        // 将审核信息添加到审核表中
        user.setPhonenumber(sysUser.getPhonenumber());
        user.setInvitePeople(sysUser.getInvitePeople());
        user.setCreateTime(DateUtils.getNowDate());
        user.setDesignerType(sysUser.getDesignerType());
        auditService.addRealInfo(user);

        return R.ok();
    }


    // 审核通过,审核不通过
    @PostMapping ("/audit")
    @SaCheckPermission("audit:data:audit")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> audit(@RequestBody Audit audit) {
        SysUser sysUser = userService.selectById(audit.getUserId());
        Audit result =  auditService.selectByUserId(audit.getUserId());

        // 如果审核通过，将信息存储到用户表中，再将审核状态改变为未审核
        if("yes".equals(audit.getAudit())) {
            sysUser.setRegisStatus("审核通过");
            sysUser.setName(result.getName());
            sysUser.setQq(result.getQq());
            sysUser.setQqMail(result.getQqMail());
            sysUser.setZfb(result.getZfb());
            sysUser.setWork(result.getWork());
            sysUser.setProficient(result.getProficient());
        }else{
            sysUser.setRegisStatus("审核不通过");
        }
        auditService.deleteById(result.getId());
        userService.updateUser1(sysUser);
        return R.ok();
    }

    // 重新审核
    @GetMapping("/resetAudit/{id}")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> resetAudit(@PathVariable("id") Long userId){
        SysUser sysUser = userService.selectById(userId);
        sysUser.setRegisStatus("注册中");
        userService.updateUser1(sysUser);
        return R.ok();
    }

    @GetMapping("/getList/{id}")
    public R<List<Audit> > getList(@PathVariable("id") String InvitePeople) {
        // 通过查询缓存来判断是不是添加
        return R.ok(auditService.getListByUserName(InvitePeople));
    }
}
