package cn.iocoder.yudao.module.bpm.dal.mysql.oaWop;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oaWop.OaWopDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo.*;

/**
 * 申请加班工资业务 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OaWopMapper extends BaseMapperX<OaWopDO> {

    default PageResult<OaWopDO> selectPage(OaWopPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OaWopDO>()
                .eqIfPresent(OaWopDO::getUserId, reqVO.getUserId())
                .eqIfPresent(OaWopDO::getType, reqVO.getType())
                .eqIfPresent(OaWopDO::getReason, reqVO.getReason())
                .betweenIfPresent(OaWopDO::getStartTime, reqVO.getBeginStartTime(), reqVO.getEndStartTime())
                .betweenIfPresent(OaWopDO::getEndTime, reqVO.getBeginEndTime(), reqVO.getEndEndTime())
                .eqIfPresent(OaWopDO::getDay, reqVO.getDay())
                .eqIfPresent(OaWopDO::getResult, reqVO.getResult())
                .eqIfPresent(OaWopDO::getProcessInstanceId, reqVO.getProcessInstanceId())
                .betweenIfPresent(OaWopDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(OaWopDO::getId));
    }


}
