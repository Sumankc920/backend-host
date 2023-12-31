package com.arun.ag_backend.Services;

import com.arun.ag_backend.Entities.*;
import com.arun.ag_backend.Entities.Class;
import com.arun.ag_backend.Repo.AttendanceRepo;
import com.arun.ag_backend.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private AttendanceRepo attendanceRepo;
    public void save_teacher(Users user) {

        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacherRepo.save(teacher);

//        Optional<Users> existingUser = userService.finByEmail(user.getEmail());
//
//        if(existingUser.isEmpty()){
//
//
//            userService.save_user(user);
//
//
//            teacherRepo.save(teacher);
//
//            return user;
//    }else {
//            Users ex_user = existingUser.get();
//            Optional<Teacher> ex_teacher = teacherRepo.findByUserEmail(ex_user.getEmail());
//            Teacher t = ex_teacher.get();
//            teacherRepo.delete(t);
//            if(!ex_user.isEnabled()){
//                userService.delete_user(ex_user);
//                userService.save_user(user);
//                teacherRepo.save(teacher);
//
//                return user;
//            }else
//            {
//
//                return null;
//            }
//    }
    }


    public Optional<List<Object>> findAttendance(String sub_name , LocalDate date , int class_id){

        return attendanceRepo.findBySubjectAndDate(sub_name, date, class_id);
    }


    public List<Class> get_teacher_classes(String email){
        List<Class> teacher_subjects =   teacherRepo.findTeacherSubjectsByEmail(email);


        return  teacher_subjects;
    }

    public Subject get_teacher_subject(String email , int class_id){
        Subject subjects = teacherRepo.findTeacherSubject(class_id , email);
        System.out.println(subjects.toString());
        return  subjects;
    }
}
