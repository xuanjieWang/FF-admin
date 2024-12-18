package org.dromara.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.domain.AOrder;
import org.dromara.domain.bo.AOrderBo;
import org.dromara.domain.vo.AOrderVo;
import org.dromara.service.IAOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【请填写功能名称】
 *
 * @author Lion Li
 * @date 2024-04-16
 */
@RestController
@RequestMapping("/system/order")
public class OrderController extends BaseController {

    @Autowired
    private IAOrderService aOrderService;

    /**
     * 查询【请填写功能名称】列表
     */
    @SaCheckPermission("system:order:list")
    @GetMapping("/list")
    public TableDataInfo<AOrderVo> list(AOrderBo bo, PageQuery pageQuery) {
        return aOrderService.queryPageList(bo, pageQuery);
    }
    @SaCheckPermission("system:order:list")
    @Transactional(rollbackFor =Exception.class)
    @GetMapping("/listHis")
    public TableDataInfo<AOrderVo> listHis(AOrderBo bo, PageQuery pageQuery) {
        return aOrderService.listHis(bo, pageQuery);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @SaCheckPermission("system:order:export")
    @Log(title = "导出订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AOrderBo bo, HttpServletResponse response) {
        List<AOrderVo> list = aOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "【请填写功能名称】", AOrderVo.class, response);
    }

    /**
     * 获取【请填写功能名称】详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:order:query")
    @GetMapping("/{id}")
    public R<AOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(aOrderService.queryById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @SaCheckPermission("system:order:add")
    @Log(title = "添加订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Transactional(rollbackFor = Exception.class)
    public R<Void> add(@RequestBody AOrder order) {
        return toAjax(aOrderService.insertByBo(order));
    }

    /**
     * 修改【请填写功能名称】
     */
    @SaCheckPermission("system:order:edit")
    @Log(title = "修改订单信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Transactional(rollbackFor = Exception.class)
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AOrderBo bo) {
        return toAjax(aOrderService.updateByBo(bo));
    }

    @Log(title = "提交评论", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/subcomm")
    @Transactional(rollbackFor = Exception.class)
    public R<Void> subcomm(@Validated(EditGroup.class) @RequestBody AOrderBo bo) {
        return toAjax(aOrderService.subcomm(bo));
    }

    /**
     * 删除【请填写功能名称】
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:order:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(aOrderService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
        查询需要提现的订单
     */
    @SaCheckPermission("system:order:getTxOrder")
    @PostMapping("/getTxOrder")
    public R<List<AOrder>> getTxOrder(AOrderVo orderVo) {
        return R.ok(aOrderService.getTxOrder((orderVo)));
    }

}
