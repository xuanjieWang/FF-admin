//package org.dromara.web.controller;
//
//import cn.dev33.satoken.annotation.SaIgnore;
//import com.alibaba.fastjson.JSONObject;
//import lombok.RequiredArgsConstructor;
//import org.dromara.common.core.domain.R;
//import org.dromara.common.core.utils.StringUtils;
//import org.dromara.common.redis.utils.RedisUtils;
//import org.dromara.common.sms.config.SendSmsUtils;
//import org.dromara.common.sms.config.Sms;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Duration;
//import java.time.Instant;
//
//@SaIgnore
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/sms/data")
//public class SMSController {
//    private String smsCode = "global:captcha_codes:";
//
//    @GetMapping("/getSmsCode")
//    public R<String> getSmsCode(String phone) {
//        String code = RedisUtils.getCacheObject(smsCode + phone);
//
//        // 判断是否频繁
//        int count = 1;
//        if (StringUtils.isNotBlank(code)) {
//            Sms sms = JSONObject.toJavaObject(JSONObject.parseObject(code), Sms.class);
//            count = sms.getCount();
//            Long limit = (long) (SendSmsUtils.times.get(sms.getCount()) * 60000);
//            boolean b = Instant.now().toEpochMilli() < limit + sms.getCreateTime();
//            if (b) {
//                return R.fail("验证码发送频繁，请稍后再试！");
//            }
//        }
//        String res;
//        try {
//            res = SendSmsUtils.sendSms(phone);
//        } catch (Exception e) {
//            return R.fail("验证码发送失败，服务错误！");
//        }
//
//        Sms sms = new Sms();
//        sms.setCode(res);
//        sms.setCreateTime(Instant.now().toEpochMilli());
//        sms.setCount(count);
//        RedisUtils.setCacheObject(smsCode + phone, sms, Duration.ofMinutes(60));
//        return R.ok(res);
//    }
//
//
//}
