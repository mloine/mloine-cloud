package com.mloine.msuser.repository;

import com.mloine.msuser.domain.entity.User;
import com.mloine.msuser.domain.entity.UserAccountEventLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountEventLogRepository extends CrudRepository<UserAccountEventLog, Integer> {

}
