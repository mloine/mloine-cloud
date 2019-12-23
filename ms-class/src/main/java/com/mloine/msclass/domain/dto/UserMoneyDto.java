package com.mloine.msclass.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 *  @Author: XueYongKang
 *  @Description:    用户余额扣减模型
 *  @Data: 2019/12/19 14:25
 */
@Getter
@Setter
public class UserMoneyDto {

    private Integer userId;
    /**
     * 需要扣减的金额
     */
    private BigDecimal money;

    private String event;

    private String description;

    public UserMoneyDto() {
    }

    public UserMoneyDto(Integer userId, BigDecimal money, String event, String description) {
        this.userId = userId;
        this.money = money;
        this.event = event;
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserMoneyDto{" +
                "userId=" + userId +
                ", money=" + money +
                ", event='" + event + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
