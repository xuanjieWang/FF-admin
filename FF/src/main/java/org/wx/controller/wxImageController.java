package org.wx.controller;

import org.dromara.common.core.domain.R;
import org.dromara.common.web.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wx.domain.WxImage;
import org.wx.service.WxImageService;

import java.util.List;


@RestController
@RequestMapping("/wx/image")
public class wxImageController extends BaseController {

    @Autowired
    private WxImageService wxImageService;
    @GetMapping("/{type}")
    public R<List<WxImage>> getImgByType(@PathVariable("type") String type) {
        return R.ok(wxImageService.getImageByType(type));
    }

    // 图片上传
//    @GetMapping("/upload")
//    public R<List<WxImage>> getImgByType(@PathVariable("type") String type) {
//        return R.ok(wxImageService.getImageByType(type));
//    }
}
