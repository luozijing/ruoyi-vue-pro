package cn.iocoder.yudao.module.bpm.service.oaWop;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo.*;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oaWop.OaWopDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 申请加班工资业务 Service 接口
 *
 * @author 芋道源码
 */
public interface OaWopService {

    /**
     * 创建申请加班工资业务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOaWop(@Valid OaWopCreateReqVO createReqVO);

    /**
     * 更新申请加班工资业务
     *
     * @param updateReqVO 更新信息
     */
    void updateOaWop(@Valid OaWopUpdateReqVO updateReqVO);

    /**
     * 更新申请加班工资业务结果--监听器监听
     *
     * @param id 对应业务表id
     * @param result 申请结果
     */
    void updateOaWopResult(Long id, Integer result);


    /**
     * 删除申请加班工资业务
     *
     * @param id 编号
     */
    void deleteOaWop(Long id);

    /**
     * 获得申请加班工资业务
     *
     * @param id 编号
     * @return 申请加班工资业务
     */
    OaWopDO getOaWop(Long id);

    /**
     * 获得申请加班工资业务列表
     *
     * @param ids 编号
     * @return 申请加班工资业务列表
     */
    List<OaWopDO> getOaWopList(Collection<Long> ids);

    /**
     * 获得申请加班工资业务分页
     *
     * @param pageReqVO 分页查询
     * @return 申请加班工资业务分页
     */
    PageResult<OaWopDO> getOaWopPage(OaWopPageReqVO pageReqVO);

}
