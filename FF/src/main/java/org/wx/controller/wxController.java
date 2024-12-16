package org.wx.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.domain.bo.AAccountBo;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AAccountVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/data")
public class wxController extends BaseController {


//    // 分页查询出小程序
//    @GetMapping("/getPage")
//    public TableDataInfo<AAccountVo> listAccount(AAccountBo bo, PageQuery pageQuery) {
//    }
//
//    //扣除订单金额
//    @SaCheckPermission("system:order:list")
//    @GetMapping("/DedAccount")
//    public R<Void> DedAccount(AOrderBo bo) {
//        return toAjax(accountService.DedAccount(bo));
//    }
}
