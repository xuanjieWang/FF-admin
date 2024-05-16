package org.dromara.task;

import jakarta.annotation.PostConstruct;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.domain.vo.MoneyVo;
import org.dromara.system.domain.SysUser;
import org.dromara.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class InviteCodeTask {
    private final static Logger log = LoggerFactory.getLogger(InviteCodeTask.class);

    @Autowired
    private ISysUserService sysUserService;

    @PostConstruct
    public void init() {

        // 初始化的时候没有邀请码就进行初始化
        if (!RedisUtils.hasKey("InviteCode: ")) {
            InitInviteCode();
        }

        // 设置提现时间
        if (!RedisUtils.hasKey("txTime: ")) {
            InitTxTime();
        }


    }
    @Scheduled(cron = "0 04 30 * * ?")
    public void InitInviteCode() {
        List<SysUser> list = new ArrayList();
        List<SysUser> users = sysUserService.selectList();
        users.forEach(item ->{
            SysUser user = new SysUser();
            user.setUserId(item.getUserId());
            user.setUserName(item.getUserName());
            user.setInviteCode(getInviteCode());
            list.add(user);
        });
        RedisUtils.deleteKeys("InviteCode: ");
        RedisUtils.setCacheList("InviteCode: ", list);
        log.info("执行初始化邀请码---InitInviteCode()  success");

        List<SysUser> list1= RedisUtils.getCacheList("InviteCode: ");
        list1.forEach(item->{
            System.out.println(item.getUserName()+item.getNickName());
        });
    }

    public  String getInviteCode(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public void InitTxTime(){
        // 提现时间, 默认是月
        MoneyVo moneyVo = new MoneyVo();
        moneyVo.setType("month");
        moneyVo.setBeginTime(5);
        moneyVo.setEndTime(10);
        RedisUtils.setCacheObject("txTime: ", moneyVo);
        log.info("初始化提现时间------success");
    }
}

