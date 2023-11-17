package com.greenie.dashboard.repository;

import com.greenie.dashboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("FROM User WHERE LOWER(name) LIKE %:searchName%")
    List<User> findByName(@Param("searchName") String name);

    @Query("FROM User WHERE LOWER(email) LIKE %:searchEmail%")
    List<User> findByEmail(@Param("searchEmail") String email);

    @Query("FROM User WHERE phone LIKE %:searchPhone%")
    List<User> findByPhone(@Param("searchPhone") String phone);
}
