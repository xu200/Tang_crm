package com.xqy.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @TableName t_customer_loss
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("customer_loss")
@ApiModel(value="CustomerLoss对象", description="")
public class XqyCustomerLoss implements Serializable {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "客户编号")
    private String cusNo;

    @ApiModelProperty(value = "客户姓名")
    private String cusName;

    @ApiModelProperty(value = "客户经理")
    private String cusManager;

    @ApiModelProperty(value = "最后下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastOrderTime;

    @ApiModelProperty(value = "确认流失时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date confirmLossTime;

    @ApiModelProperty(value = "流失状态")
    private Integer state;

    @ApiModelProperty(value = "流失原因")
    private String lossReason;

    @ApiModelProperty(value = "有效状态")
    @TableLogic //逻辑删除
    private Integer isValid;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}