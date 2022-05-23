package cn.iocoder.yudao.module.bpm.service.oaWop.listener;

import cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo.OaWopUpdateReqVO;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOALeaveService;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOALeaveServiceImpl;
import cn.iocoder.yudao.module.bpm.service.oaWop.OaWopService;
import cn.iocoder.yudao.module.bpm.service.oaWop.OaWopServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class BpmOAOwpResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private OaWopService owpService;

    @Override
    protected String getProcessDefinitionKey() {
        return OaWopServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        log.info("onEvent {}", event);
        owpService.updateOaWopResult(Long.parseLong(event.getBusinessKey()), event.getResult());
    }

}
