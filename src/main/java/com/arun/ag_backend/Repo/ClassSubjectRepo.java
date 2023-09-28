package com.arun.ag_backend.Repo;

import com.arun.ag_backend.Entities.Class;
import com.arun.ag_backend.Entities.ClassSubjects;
import com.arun.ag_backend.Entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassSubjectRepo  extends JpaRepository<ClassSubjects , Integer> {

    @Query("select s from ClassSubjects s where s.aClass = :aclass")
    Optional<Subject> findBySubject(@Param("aclass") Class aclass );


}
