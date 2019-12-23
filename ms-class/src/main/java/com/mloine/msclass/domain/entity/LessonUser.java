package com.mloine.msclass.domain.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "lesson_user")
@Entity
@Getter
@Setter
public class LessonUser {

  @Column
  @Id
  //这个字段自增加
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer lessonId;

  @Column
  private Integer userId;



}
