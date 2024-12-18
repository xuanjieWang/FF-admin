package org.dromara.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.domain.ATx;
import org.dromara.domain.bo.ATxBo;
import org.dromara.domain.vo.ATxVo;
import org.dromara.service.IATxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tx/data")
public class TxController {

    @Autowired
    private IATxService txService;

    // 查看提现记录
    @SaCheckPermission("tx:data:list")
    @GetMapping("/list")
    public TableDataInfo<ATxVo> list(ATxBo bo, PageQuery pageQuery) {
        return txService.queryPageList(bo, pageQuery);
    }


    // 查看扣款记录
    @SaCheckPermission("tx:data:list")
    @GetMapping("/listDis")
    public TableDataInfo<ATxVo> listDis(ATxBo bo, PageQuery pageQuery) {
        return txService.queryPageListDis(bo, pageQuery);
    }


    //审核
    @SaCheckPermission("tx:data:adopt")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/adopt")
    public R<Void> adopt(@RequestBody ATx bo) {
        return txService.adopt(bo);
    }

    // 提现申请
    @SaCheckPermission("tx:data:setTx")
    @PostMapping("/setTx")
    @Transactional(rollbackFor = Exception.class)
    public R setTx(@RequestBody ATx bo) {
        return txService.setTx(bo);
    }

    // 查询当前用户的扣款订单，订单时间
    @SaCheckPermission("tx:data:list")
    @PostMapping("/getDisOrderList")
    @Transactional(rollbackFor = Exception.class)
    public R getDisOrderList(@RequestBody ATx bo) {
        return txService.getDisOrderList(bo);
    }


}
