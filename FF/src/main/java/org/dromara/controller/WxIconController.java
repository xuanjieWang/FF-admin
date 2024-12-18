package org.dromara.controller;

import org.dromara.common.core.domain.R;
import org.dromara.common.web.core.BaseController;
import org.dromara.domain.WxImage;
import org.dromara.service.IWxImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/wx/icon")
public class WxIconController extends BaseController {

    @Autowired
    private IWxImageService wxImageService;
    @GetMapping("/getCarouselImg")
    public R<List<WxImage>> getCarouselImg() {
        return R.ok(wxImageService.getImageByType("轮播图"));
    }
}
