package org.dromara.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.domain.bo.AAccountBo;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AAccountVo;
import org.dromara.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/data")
public class AccountController extends BaseController {
    @Autowired
    private IAccountService accountService;

    /**
     * 查询【请填写功能名称】列表
     */
    @SaCheckPermission("system:order:list")
    @GetMapping("/listAccount")
    public TableDataInfo<AAccountVo> listAccount(AAccountBo bo, PageQuery pageQuery) {
        return accountService.queryPageList(bo, pageQuery);
    }

    //扣除订单金额
    @SaCheckPermission("system:order:list")
    @GetMapping("/DedAccount")
    public R<Void> DedAccount(AOrderBo bo) {
       return toAjax(accountService.DedAccount(bo));
    }

}
