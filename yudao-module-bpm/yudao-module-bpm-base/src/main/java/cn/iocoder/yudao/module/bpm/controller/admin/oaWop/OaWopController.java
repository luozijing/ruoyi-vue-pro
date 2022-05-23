package cn.iocoder.yudao.module.bpm.controller.admin.oaWop;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.*;

import javax.validation.*;
import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;


import cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo.*;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oaWop.OaWopDO;
import cn.iocoder.yudao.module.bpm.convert.oaWop.OaWopConvert;
import cn.iocoder.yudao.module.bpm.service.oaWop.OaWopService;

@Api(tags = "管理后台 - 申请加班工资业务")
@RestController
@RequestMapping("/bpm/oa-wop")
@Validated
public class OaWopController {

    @Resource
    private OaWopService oaWopService;

    @PostMapping("/create")
    @ApiOperation("创建申请加班工资业务")
    @PreAuthorize("@ss.hasPermission('bpm:oa-wop:create')")
    public CommonResult<Long> createOaWop(@Valid @RequestBody OaWopCreateReqVO createReqVO) {
        return success(oaWopService.createOaWop(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新申请加班工资业务")
    @PreAuthorize("@ss.hasPermission('bpm:oa-wop:update')")
    public CommonResult<Boolean> updateOaWop(@Valid @RequestBody OaWopUpdateReqVO updateReqVO) {
        oaWopService.updateOaWop(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除申请加班工资业务")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('bpm:oa-wop:delete')")
    public CommonResult<Boolean> deleteOaWop(@RequestParam("id") Long id) {
        oaWopService.deleteOaWop(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得申请加班工资业务")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('bpm:oa-wop:query')")
    public CommonResult<OaWopRespVO> getOaWop(@RequestParam("id") Long id) {
        OaWopDO oaWop = oaWopService.getOaWop(id);
        return success(OaWopConvert.INSTANCE.convert(oaWop));
    }

    @GetMapping("/list")
    @ApiOperation("获得申请加班工资业务列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('bpm:oa-wop:query')")
    public CommonResult<List<OaWopRespVO>> getOaWopList(@RequestParam("ids") Collection<Long> ids) {
        List<OaWopDO> list = oaWopService.getOaWopList(ids);
        return success(OaWopConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得申请加班工资业务分页")
    @PreAuthorize("@ss.hasPermission('bpm:oa-wop:query')")
    public CommonResult<PageResult<OaWopRespVO>> getOaWopPage(@Valid OaWopPageReqVO pageVO) {
        PageResult<OaWopDO> pageResult = oaWopService.getOaWopPage(pageVO);
        return success(OaWopConvert.INSTANCE.convertPage(pageResult));
    }

}
