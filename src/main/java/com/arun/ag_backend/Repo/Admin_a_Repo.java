package com.arun.ag_backend.Repo;

import com.arun.ag_backend.Entities.Admin_assigned_Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Admin_a_Repo extends JpaRepository<Admin_assigned_Users , Integer> {

    @Query("select  u from  Admin_assigned_Users u where u.email = :email")
    Optional<Admin_assigned_Users> findByEmail(@Param("email") String email);


    @Query("SELECT s.short_name FROM Subject s " +
            " JOIN ClassSubjects  cs on s.subject_id = cs.subject.subject_id " +
            " JOIN Class c on c.class_id = cs.aClass.class_id " +
            "WHERE c.semester = :semester AND c.shift = :shift")
    List<String> findNamesForSemesterAndShift(@Param("semester") int semester , @Param("shift") String shift);

    @Query("SELECT u.email , u.name FROM Users u" +
            " JOIN Student  s on s.user.id = u.id " +
            " join Teacher t on t.user.id = u.id "+
            " JOIN Class c on c.class_id = s.aClass.class_id " +
            " join TeacherSubjects ts on ts.aClass.class_id = c.class_id " +
            " join Subject  st on st.subject_id = ts.subject.subject_id " +
            "WHERE c.semester = :semester AND c.shift = :shift and st.short_name = :sub_name ")
    List<Object> findSubjectAllDetails(@Param("sub_name") String sub_name , @Param("semester") int semester , @Param("shift") String shift);

    @Query("select  a from  Admin_assigned_Users a  where a.email = :email and a.aClass.class_id = :class_id")
    Optional<Admin_assigned_Users> findByStudent(@Param("email") String email , @Param("class_id") int class_id);

    @Transactional
    @Modifying
    @Query("UPDATE Admin_assigned_Users u SET u.aClass.class_id = :class_id WHERE u.email = :email")
    void updateClassIdForUser(@Param("newClassId") int class_id, @Param("email") String email);

}
