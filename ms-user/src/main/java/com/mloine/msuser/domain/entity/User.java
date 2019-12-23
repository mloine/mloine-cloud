package com.mloine.msuser.domain.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user")
@Entity
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String username;
  @Column
  private String password;
  @Column
  private BigDecimal money;
  @Column
  private String role;
  @Column
  private Date regTime;


}
