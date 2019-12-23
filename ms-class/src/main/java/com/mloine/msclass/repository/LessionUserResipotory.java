package com.mloine.msclass.repository;

import com.mloine.msclass.domain.entity.LessonUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessionUserResipotory extends CrudRepository<LessonUser,Integer> {
    LessonUser findByLessonId(Integer id);
}
