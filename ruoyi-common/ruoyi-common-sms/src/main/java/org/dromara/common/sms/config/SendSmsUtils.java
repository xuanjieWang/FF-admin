package org.dromara.common.sms.config;



/**
 * java使用阿里云短信服务接口 注:代码直接复制与阿里云提供的调用示例
 *
 * @return 发送SMS响应信息
 * @throws ClientException
 * 客户端异常
 * @date 2018年7月15日 下午9:33:33
 */

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.List;

/**
 * 发送短信工具类。将创建手机号的数据
 * 第一次30s
 * 第二次120s
 * 第三次5分钟
 * 第四次30分钟
 * 第五次1小时
 *
 * 发送短信返回验证码，rediskey
 * 注册接口进行比对，使用rediskey和验证码进行比对
 * **/

public class SendSmsUtils {

    /**
     * 第一次 1分
     * 第二次 3分
     * 第三次 5分钟
     * 第四次 30分钟
     * 第五次 60分钟
     * **/
    public static final List<Integer> times = List.of(0,1,3,5,30,60);
    public static String sendSms(String phone) {
        // 设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient需要的几个参数
        final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
        // 你的AccessKeyID：
        final String accessKeyId = "your AccessKeyID";
        // 你的AccessKeySecret
        final String accessKeySecret = "your AccessKeySecret";
        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {

            return null;
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
        // 批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；
        // 发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phone);
        // 必填:短信签名-需在控制台新建并配置模板
        request.setSignName("your 签名");
        // 必填:短信模板-需在控制台新建并配置模板
        request.setTemplateCode("your 模板code");
        // 可选:模板中的变量替换JSON串,如模板内容为"您的验证码为${code},5分钟内有效，请及时校验!"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,
        // 比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"code\":\"54188\"}");
        // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        // request.setOutId("yourOutId");
        // 发送短信调用请求(如果请求失败,这里会抛ClientException异常)
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            return null;
        }
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            System.out.println("短信发送成功!");
            return sendSmsResponse.getCode();
        }
        System.out.println("短信发送失败!");
        return null;
    }


}