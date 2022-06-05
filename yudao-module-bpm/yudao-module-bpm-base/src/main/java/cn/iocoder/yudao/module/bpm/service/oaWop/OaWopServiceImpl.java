package cn.iocoder.yudao.module.bpm.service.oaWop;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oa.BpmOALeaveDO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo.*;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oaWop.OaWopDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.bpm.convert.oaWop.OaWopConvert;
import cn.iocoder.yudao.module.bpm.dal.mysql.oaWop.OaWopMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.*;

/**
 * 申请加班工资业务 Service 实现类
 *
 */
@Service
@Validated
public class OaWopServiceImpl implements OaWopService {

    @Resource
    private OaWopMapper oaWopMapper;

    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    /**
     * 流程定义 KEY
     */
    public static final String PROCESS_KEY = "oa_owp";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOaWop(OaWopCreateReqVO createReqVO) {
        Long userID = getLoginUserId();
        // 插入
        long day = DateUtil.betweenDay(createReqVO.getStartTime(), createReqVO.getEndTime(), false);
        OaWopDO oaWop = OaWopConvert.INSTANCE.convert(createReqVO).setUserId(userID).setDay((int) day)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        oaWopMapper.insert(oaWop);

        // 发起 BPM 流程 生成流程实例
        Map<String, Object> processInstanceVariables = new HashMap<>();
        processInstanceVariables.put("workTime", day);
        // ${var:containsAny(project, 3,4)} 传入的list变量是否包含 3 4
        List<Integer> vars = new ArrayList<>();
        vars.add(createReqVO.getType());
        processInstanceVariables.put("project", vars);
        String processInstanceId = processInstanceApi.createProcessInstance(userID,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(oaWop.getId())));

        // 将工作流的编号，更新到 OA 请假单中
        oaWopMapper.updateById(new OaWopDO().setId(oaWop.getId()).setProcessInstanceId(processInstanceId));

        return oaWop.getId();
    }

    @Override
    public void updateOaWop(OaWopUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateOaWopExists(updateReqVO.getId());
        // 更新
        OaWopDO updateObj = OaWopConvert.INSTANCE.convert(updateReqVO);
        oaWopMapper.updateById(updateObj);
    }

    @Override
    public void updateOaWopResult(Long id, Integer result) {
        // 校验存在
        this.validateOaWopExists(id);
        // 更新
        oaWopMapper.updateById(new OaWopDO().setId(id).setResult(result));
    }

    @Override
    public void deleteOaWop(Long id) {
        // 校验存在
        this.validateOaWopExists(id);
        // 删除
        oaWopMapper.deleteById(id);
    }

    private void validateOaWopExists(Long id) {
        if (oaWopMapper.selectById(id) == null) {
            throw exception(OA_OWP_NOT_EXISTS);
        }
    }

    @Override
    public OaWopDO getOaWop(Long id) {
        return oaWopMapper.selectById(id);
    }

    @Override
    public List<OaWopDO> getOaWopList(Collection<Long> ids) {
        return oaWopMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OaWopDO> getOaWopPage(OaWopPageReqVO pageReqVO) {
        return oaWopMapper.selectPage(pageReqVO);
    }


}
