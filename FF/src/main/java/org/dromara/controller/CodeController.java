package org.dromara.controller;

import com.jdcloud.sdk.utils.StringUtils;
import org.dromara.common.core.domain.R;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.system.domain.SysUser;
import org.dromara.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/regis/code")
public class CodeController {
    @Autowired private ISysUserService sysUserService;
    @RequestMapping("/{userName}")
    public R<String> getCode(@PathVariable String userName) {
        List<SysUser> list = RedisUtils.getCacheList("InviteCode: ");
        StringBuilder code = new StringBuilder();

        list.forEach(item -> {
            if (item.getUserName().equals(userName)) {
                code.append(item.getInviteCode());
            }
        });
        return (StringUtils.isNotBlank(code.toString()) ? R.ok(code.toString()) : R.warn("邀请码不存在"));
    }
}

