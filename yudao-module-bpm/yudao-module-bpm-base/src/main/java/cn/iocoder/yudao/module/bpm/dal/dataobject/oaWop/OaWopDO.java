package cn.iocoder.yudao.module.bpm.dal.dataobject.oaWop;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 申请加班工资业务 DO
 *
 * @author 芋道源码
 */
@TableName("bpm_oa_wop")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OaWopDO extends BaseDO {

    /**
     * 加班工资申请表单主键
     */
    @TableId
    private Long id;
    /**
     * 申请人的用户编号
     */
    private Long userId;
    /**
     * 请假项目
     */
    private Integer type;
    /**
     * 加班原因
     */
    private String reason;
    /**
     * 加班开始时间
     */
    private Date startTime;
    /**
     * 加班结束时间
     */
    private Date endTime;
    /**
     * 加班天数
     */
    private Integer day;
    /**
     * 申请结果
     */
    private Integer result;
    /**
     * 流程实例的编号
     */
    private String processInstanceId;

}
