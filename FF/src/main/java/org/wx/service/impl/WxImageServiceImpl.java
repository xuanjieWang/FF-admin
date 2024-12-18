package org.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wx.domain.WxImage;
import org.wx.mapper.WxImageMapper;
import org.wx.service.WxImageService;

import java.util.List;

@Service
public class WxImageServiceImpl extends ServiceImpl<WxImageMapper, WxImage> implements WxImageService {
    @Override
    public List<WxImage> getImageByType(String type) {
        LambdaQueryWrapper<WxImage> lqw = new LambdaQueryWrapper<>();
        lqw.eq(WxImage::getType, type);
        lqw.orderByAsc(WxImage::getSort);
        return list(lqw);
    }
}
