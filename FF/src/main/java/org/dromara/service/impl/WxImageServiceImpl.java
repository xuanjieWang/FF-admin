package org.dromara.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.domain.WxImage;
import org.dromara.mapper.WxImageMapper;
import org.dromara.service.IWxImageService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class WxImageServiceImpl extends ServiceImpl<WxImageMapper, WxImage> implements IWxImageService {
    @Override
    public List<WxImage> getImageByType(String type) {
        LambdaQueryWrapper<WxImage> lqw = new LambdaQueryWrapper<>();
        lqw.eq(WxImage::getType, type);
        lqw.orderByAsc(WxImage::getSort);
        return list(lqw);
    }
}
