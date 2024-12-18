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
    @Scheduled(cron = "0 30 3 * * ?")
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
            System.out.println(item.getUserName()+"--------"+item.getUserId()+"------"+item.getInviteCode());
        });
    }


    // 每10分钟刷新一次验证码
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void checkCode() {
        List<SysUser> list= RedisUtils.getCacheList("InviteCode: ");
        List<SysUser> users = sysUserService.selectUserByCreateTime();

        // 不需要更新验证码
        if(list.size() == users.size()) return;
        for (int i = 0; i < (users.size() - list.size()); i++){
            SysUser user = users.get(i);
            if(user.getInviteCode() == null){
                SysUser item = new SysUser();
                item.setUserId(user.getUserId());
                item.setUserName(user.getUserName());
                item.setInviteCode(getInviteCode());
                list.add(user);
            }
        }
        RedisUtils.deleteKeys("InviteCode: ");
        RedisUtils.setCacheList("InviteCode: ", list);
        log.info("执行检查邀请码---add邀请码()  success: count----"+(users.size() - list.size()));

    }


    public static String getInviteCode(){
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

