package org.dromara.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.domain.R;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.web.core.BaseController;
import org.dromara.domain.vo.MoneyVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/money")
public class MoneyController extends BaseController {
    @RequestMapping("/getTime")
    public R<MoneyVo> getList() {
        MoneyVo vo = RedisUtils.getCacheObject("txTime: ");
        return R.ok(vo);
    }

    @SaCheckPermission("system:money:setTime")
    @RequestMapping("/setTxTime")
    public R<Void> getList(MoneyVo moneyVo) {
        if (moneyVo.getBeginTime() > 0 && moneyVo.getEndTime() > 0) {
            RedisUtils.setCacheObject("txTime: ", moneyVo);
            return R.ok();
        }
        return R.fail("提现时间设置错误！--");
    }
}
