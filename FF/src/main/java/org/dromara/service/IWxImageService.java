package org.dromara.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.dromara.domain.WxImage;

import java.util.List;

public interface IWxImageService extends IService<WxImage> {
    List<WxImage> getImageByType(String type);
}
