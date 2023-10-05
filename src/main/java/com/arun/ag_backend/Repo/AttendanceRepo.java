package com.arun.ag_backend.Repo;

import com.arun.ag_backend.Entities.Attendance;
import com.arun.ag_backend.Entities.Class;
import com.arun.ag_backend.Entities.ClassRoutine;
import com.arun.ag_backend.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {

    @Query("SELECT COUNT(a) > 0 FROM Attendance a " +
            "WHERE a.student = :student " +
            "AND a.classRoutine = :classRoutine " +
            "AND a.date = :date ")

    boolean existsSameAttendance(
            @Param("student") Student student,
            @Param("classRoutine") ClassRoutine classRoutine,
            @Param("date") LocalDate date

    );


    @Query("SELECT a.student.user.email , a.student.user.name FROM Attendance a " +
            "WHERE a.classRoutine.subject.short_name = :subjectName " +
            "AND a.date = :date " +
            "AND a.classRoutine.aClass.class_id = :class_id")
    List<String> findBySubjectAndDate(
            @Param("subjectName") String subjectName,
            @Param("date") LocalDate date ,
            @Param("class_id") int class_id
    );


    @Query("select a.status from Attendance a " +
            "where a.student.roll = :roll " +
            "and a.date =:date " +
            "and a.classRoutine.subject.short_name = :sub_name"
     )
    String findStudentAttendaance(@Param("date") LocalDate date , @Param("roll") int roll , @Param("sub_name") String sub_name);

}
