package cn.iocoder.yudao.module.bpm.service.oaWop;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo.*;
import cn.iocoder.yudao.module.bpm.dal.dataobject.oaWop.OaWopDO;
import cn.iocoder.yudao.module.bpm.dal.mysql.oaWop.OaWopMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link OaWopServiceImpl} 的单元测试类
*
* @author 芋道源码
*/
@Import(OaWopServiceImpl.class)
public class OaWopServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OaWopServiceImpl oaWopService;

    @Resource
    private OaWopMapper oaWopMapper;

    @Test
    public void testCreateOaWop_success() {
        // 准备参数
        OaWopCreateReqVO reqVO = randomPojo(OaWopCreateReqVO.class);

        // 调用
        Long oaWopId = oaWopService.createOaWop(reqVO);
        // 断言
        assertNotNull(oaWopId);
        // 校验记录的属性是否正确
        OaWopDO oaWop = oaWopMapper.selectById(oaWopId);
        assertPojoEquals(reqVO, oaWop);
    }

    @Test
    public void testUpdateOaWop_success() {
        // mock 数据
        OaWopDO dbOaWop = randomPojo(OaWopDO.class);
        oaWopMapper.insert(dbOaWop);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OaWopUpdateReqVO reqVO = randomPojo(OaWopUpdateReqVO.class, o -> {
            o.setId(dbOaWop.getId()); // 设置更新的 ID
        });

        // 调用
        oaWopService.updateOaWop(reqVO);
        // 校验是否更新正确
        OaWopDO oaWop = oaWopMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, oaWop);
    }

    @Test
    public void testUpdateOaWop_notExists() {
        // 准备参数
        OaWopUpdateReqVO reqVO = randomPojo(OaWopUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> oaWopService.updateOaWop(reqVO), OA_OWP_NOT_EXISTS);
    }

    @Test
    public void testDeleteOaWop_success() {
        // mock 数据
        OaWopDO dbOaWop = randomPojo(OaWopDO.class);
        oaWopMapper.insert(dbOaWop);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOaWop.getId();

        // 调用
        oaWopService.deleteOaWop(id);
       // 校验数据不存在了
       assertNull(oaWopMapper.selectById(id));
    }

    @Test
    public void testDeleteOaWop_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> oaWopService.deleteOaWop(id), OA_OWP_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOaWopPage() {
       // mock 数据
       OaWopDO dbOaWop = randomPojo(OaWopDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setType(null);
           o.setReason(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setDay(null);
           o.setResult(null);
           o.setProcessInstanceId(null);
           o.setCreateTime(null);
       });
       oaWopMapper.insert(dbOaWop);
       // 测试 userId 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setUserId(null)));
       // 测试 type 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setType(null)));
       // 测试 reason 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setReason(null)));
       // 测试 startTime 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setEndTime(null)));
       // 测试 day 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setDay(null)));
       // 测试 result 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setResult(null)));
       // 测试 processInstanceId 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setProcessInstanceId(null)));
       // 测试 createTime 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setCreateTime(null)));
       // 准备参数
       OaWopPageReqVO reqVO = new OaWopPageReqVO();
       reqVO.setUserId(null);
       reqVO.setType(null);
       reqVO.setReason(null);
       reqVO.setBeginStartTime(null);
       reqVO.setEndStartTime(null);
       reqVO.setBeginEndTime(null);
       reqVO.setEndEndTime(null);
       reqVO.setDay(null);
       reqVO.setResult(null);
       reqVO.setProcessInstanceId(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       PageResult<OaWopDO> pageResult = oaWopService.getOaWopPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOaWop, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOaWopList() {
       // mock 数据
       OaWopDO dbOaWop = randomPojo(OaWopDO.class, o -> { // 等会查询到
           o.setUserId(null);
           o.setType(null);
           o.setReason(null);
           o.setStartTime(null);
           o.setEndTime(null);
           o.setDay(null);
           o.setResult(null);
           o.setProcessInstanceId(null);
           o.setCreateTime(null);
       });
       oaWopMapper.insert(dbOaWop);
       // 测试 userId 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setUserId(null)));
       // 测试 type 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setType(null)));
       // 测试 reason 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setReason(null)));
       // 测试 startTime 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setStartTime(null)));
       // 测试 endTime 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setEndTime(null)));
       // 测试 day 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setDay(null)));
       // 测试 result 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setResult(null)));
       // 测试 processInstanceId 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setProcessInstanceId(null)));
       // 测试 createTime 不匹配
       oaWopMapper.insert(cloneIgnoreId(dbOaWop, o -> o.setCreateTime(null)));
       // 准备参数


       // 调用
       List<OaWopDO> list = oaWopService.getOaWopList(null);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbOaWop, list.get(0));
    }

}
