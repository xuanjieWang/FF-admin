package org.dromara.controller;

import org.dromara.common.core.domain.R;
import org.dromara.common.web.core.BaseController;
import org.dromara.domain.WxImage;
import org.dromara.service.IWxImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/wx/image")
public class wxImageController extends BaseController {

    @Autowired
    private IWxImageService wxImageService;
    @GetMapping("/{type}")
    public R<List<WxImage>> getImgByType(@PathVariable String type) {
        System.out.println("type----------------------"+type);
        return R.ok(wxImageService.getImageByType(type));
    }
    @GetMapping("/test")
    public R<String> test() {
        return R.ok("wode");
    }

    // 图片上传
//    @GetMapping("/upload")
//    public R<List<WxImage>> getImgByType(@PathVariable("type") String type) {
//        return R.ok(wxImageService.getImageByType(type));
//    }

//    @RequestMapping("/{userName}")
//    public R<String> getCode(@PathVariable String userName) {
//        List<SysUser> list = RedisUtils.getCacheList("InviteCode: ");
//        StringBuilder code = new StringBuilder();
//
//        list.forEach(item -> {
//            if (item.getUserName().equals(userName)) {
//                code.append(item.getInviteCode());
//            }
//        });
//        return (StringUtils.isNotBlank(code.toString()) ? R.ok(code.toString()) : R.warn("邀请码不存在"));
//    }
}
