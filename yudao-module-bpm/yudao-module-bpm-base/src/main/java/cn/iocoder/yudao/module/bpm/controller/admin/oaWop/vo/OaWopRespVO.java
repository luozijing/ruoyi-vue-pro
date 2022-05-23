package cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 申请加班工资业务 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OaWopRespVO extends OaWopBaseVO {

    @ApiModelProperty(value = "加班工资申请表单主键", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
