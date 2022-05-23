package cn.iocoder.yudao.module.bpm.convert.oaWop;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo.*;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oaWop.OaWopDO;

/**
 * 申请加班工资业务 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface OaWopConvert {

    OaWopConvert INSTANCE = Mappers.getMapper(OaWopConvert.class);

    OaWopDO convert(OaWopCreateReqVO bean);

    OaWopDO convert(OaWopUpdateReqVO bean);

    OaWopRespVO convert(OaWopDO bean);

    List<OaWopRespVO> convertList(List<OaWopDO> list);

    PageResult<OaWopRespVO> convertPage(PageResult<OaWopDO> page);


}
