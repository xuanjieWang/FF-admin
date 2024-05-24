package org.dromara.task;

import org.dromara.domain.AOrder;
import org.dromara.service.IAOrderService;
import org.dromara.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderJsTask {
    @Autowired
    private IAOrderService orderService;

    @Autowired
    private IAccountService countService;

    private final static Logger log = LoggerFactory.getLogger(OrderJsTask.class);

    // 查询订单结算状态
    @Scheduled(cron = "0 30 2 * * ? ")
    public void checkOrderJsTask() {
        List<AOrder> order = orderService.getHYOrder();
        if (order == null) return;
        order.forEach(item -> {
            if (!countService.addAccount(item)) {
                item.setJsStatus("订单失败");
            } else {
                item.setJsStatus("已结算");
            }
            log.info("订单结算状态更新----订单核验中----"+item.getJsStatus()+"---name---"+item.getSjsName());
            orderService.updateById(item);
        });
    }
}
