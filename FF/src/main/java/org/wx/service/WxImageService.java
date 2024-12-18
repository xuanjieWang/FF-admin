package org.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wx.domain.WxImage;

import java.util.List;

public interface WxImageService extends IService<WxImage> {
    List<WxImage> getImageByType(String type);
}
