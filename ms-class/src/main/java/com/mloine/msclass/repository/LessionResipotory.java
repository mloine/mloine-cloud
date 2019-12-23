package com.mloine.msclass.repository;

import com.mloine.msclass.domain.entity.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessionResipotory extends CrudRepository<Lesson,Integer> {
}
