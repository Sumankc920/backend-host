package com.arun.ag_backend.Repo;

import com.arun.ag_backend.Entities.Admin;
import com.arun.ag_backend.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    @Query("select  a from  Admin a where a.username =:username")
    Optional<Admin> findByEmail(@Param("username") String username);
}
