package com.exam.management.exammanagementsystem.repository;

import com.exam.management.exammanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User where activeStatus = (:activeStatus) and userName =(:userName)")
    User getByUserNameAndActiveStatusTrue(@Param("activeStatus") Integer activeStatus, @Param("userName") String userName);
}
