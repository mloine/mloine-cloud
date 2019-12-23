package com.mloine.msuser.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_account_event_log")
@Entity
@Getter
@Setter
public class UserAccountEventLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private Integer userId;

  @Column
  private BigDecimal money;

  @Column
  private String event;

  @Column
  private Date createTime;

  @Column
  private String description;

  public UserAccountEventLog() {
  }

  public UserAccountEventLog(Integer userId, BigDecimal money, String event, Date createTime, String description) {
    this.userId = userId;
    this.money = money;
    this.event = event;
    this.createTime = createTime;
    this.description = description;
  }
}
