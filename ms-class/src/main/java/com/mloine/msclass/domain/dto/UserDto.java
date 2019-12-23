package com.mloine.msclass.domain.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class UserDto {

  private Integer id;


  private String username;

  private String password;

  private BigDecimal money;

  private String role;

  private Date regTime;


}
