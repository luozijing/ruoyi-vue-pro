package cn.iocoder.yudao.module.bpm.controller.admin.oaWop.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 申请加班工资业务更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OaWopUpdateReqVO extends OaWopBaseVO {

    @ApiModelProperty(value = "加班工资申请表单主键", required = true)
    @NotNull(message = "加班工资申请表单主键不能为空")
    private Long id;

}
