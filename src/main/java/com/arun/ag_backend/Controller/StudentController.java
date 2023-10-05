package com.arun.ag_backend.Controller;
import com.arun.ag_backend.Dto.HelperDTO.TeacherGetAttendance;
import com.arun.ag_backend.Entities.Attendance;
import com.arun.ag_backend.Entities.Student;
import com.arun.ag_backend.Entities.Subject;
import com.arun.ag_backend.Entities.Teacher;
import com.arun.ag_backend.Repo.AttendanceRepo;
import com.arun.ag_backend.Repo.StudentRepo;
import com.arun.ag_backend.Repo.TeacherRepo;
import com.arun.ag_backend.Services.StudentService;
import com.arun.ag_backend.Services.SubjectService;
import com.arun.ag_backend.UserDetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@CrossOrigin(origins = "${cross.origin.url}", allowCredentials = "true")
@RestController
@RequestMapping("/student")
public class StudentController {

//    Principal principal;
//    private String email =principal.getName();

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AttendanceRepo attendanceRepo;


    @RequestMapping("/classes")
    public ResponseEntity<Object> getClasses(Principal principal)  throws Exception{

        return ResponseEntity.ok(studentService.get_classes(principal.getName()));
    }

    @RequestMapping("/get_subject_teacher")
    public List<Object> get_subject_teacher(Principal principal , @RequestBody TeacherGetAttendance tbody){
        int class_id = studentService.find_class_id(principal.getName());
        Optional<Subject> subject  = subjectService.findSubjectDetails(tbody.getSub_name());
//        S   ystem.out.println(class_id)

        Optional<Student> student = studentRepo.findByUserEmail(principal.getName());

        List<Object> list = new ArrayList<>();
        if (subject.isPresent()) {
            int sub_id = subject.get().getSubject_id();
            System.out.println(sub_id);
            list.add(subject.get().getName());
            Optional<Teacher> teacher =  teacherRepo.findByUserEmail(studentService.find_teacher_name_by_subject(sub_id, class_id));
            if(teacher.isPresent()){
            list.add(teacher.get().getUser().getName());
            list.add(teacher.get().getUser().getEmail());

            if (student.isPresent()) {

                Optional<String> status = attendanceRepo.findStudentAttendaance(tbody.getDate(), student.get().getRoll(), tbody.getSub_name());

                list.add(String.valueOf(student.get().getRoll()));
                if(status.isPresent()){
                    list.add("Present");
                } else {
                    list.add("Absent");
                }
            }
            }
        }
        return list;
    }

}
