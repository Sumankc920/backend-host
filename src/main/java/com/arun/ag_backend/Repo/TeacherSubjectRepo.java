package com.arun.ag_backend.Repo;

import com.arun.ag_backend.Entities.TeacherSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherSubjectRepo extends JpaRepository<TeacherSubjects , Integer> {

//    @Query("select ts from TeacherSubjects ts where ts.subject.subject_id =:sub_id "
//        + "AND ts.aClass.class_id =: class_id"
//    )
//    TeacherSubjects findTeacherbyClassId(@Param("class_id") int class_id , @Param("sub_id") int sub_id);

    @Query("SELECT ts.users.email  from TeacherSubjects ts " +
            "WHERE ts.aClass.class_id =:class_id " +
            "AND ts.subject.subject_id =:subject_id " )
    String findTeacherBy(@Param("class_id") int class_id , @Param("subject_id") int subject_id);
}
