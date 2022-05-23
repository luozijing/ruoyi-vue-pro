package cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* 申请加班工资业务 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class OaWopBaseVO {

    @ApiModelProperty(value = "申请人的用户编号", required = true)
    private Long userId;

    @ApiModelProperty(value = "请假项目", required = true)
    @NotNull(message = "请假项目不能为空")
    private Integer type;

    @ApiModelProperty(value = "加班原因", required = true)
    @NotNull(message = "加班原因不能为空")
    private String reason;

    @ApiModelProperty(value = "加班开始时间", required = true)
    @NotNull(message = "加班开始时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date startTime;

    @ApiModelProperty(value = "加班结束时间", required = true)
    @NotNull(message = "加班结束时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date endTime;

    @ApiModelProperty(value = "加班天数", required = true)
    private Integer day;

    @ApiModelProperty(value = "申请结果", required = true)
    private Integer result;

    @ApiModelProperty(value = "流程实例的编号")
    private String processInstanceId;

}
