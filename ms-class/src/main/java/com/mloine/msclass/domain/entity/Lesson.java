package com.mloine.msclass.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lesson")
@Entity
@Getter
@Setter
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String title;

  @Column
  private String cover;

  @Column
  private BigDecimal price;

  @Column
  private String description;

  @Column
  private Date createTime;

  @Column
  private String videoUrl;




}
