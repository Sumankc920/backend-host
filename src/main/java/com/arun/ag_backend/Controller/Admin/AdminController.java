package com.arun.ag_backend.Controller.Admin;

import com.arun.ag_backend.Dto.HelperDTO.AdminSendDetails;
import com.arun.ag_backend.Dto.SubjectInfo;
import com.arun.ag_backend.Entities.*;
import com.arun.ag_backend.Entities.Class;
import com.arun.ag_backend.Repo.*;
import com.arun.ag_backend.Services.Admin_a_User_Service;
import com.arun.ag_backend.Services.ClassRoutineService;
import com.arun.ag_backend.Services.SubjectService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//@CrossOrigin(origins = "${cross.origin.url}", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private ClassRoutineService classRoutineService;

    @Autowired
    private Admin_a_User_Service admin_service;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ClassSubjectRepo classSubjectRepo;

    @Autowired
    private TeacherSubjectRepo teacherSubjectRepo;
    @Autowired
    private ClassRepo classRepo;


    @PostMapping("/add/classRoutine")
    public ResponseEntity<Object> addClassRoutine(@RequestBody JsonNode classRoutineJson){


        classRoutineService.insertRoutine(classRoutineJson);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/subjects/details")
    public List<String> find_sub_name(@RequestBody SubjectInfo subjectInfo){

            return  admin_service.get_subject_from_sem_shift(subjectInfo.getSemester() , subjectInfo.getShift());
    }

    @GetMapping("/all_teachers")
    public List<Object> findallTeacher(){

        return  teacherRepo.findAllTeachers();
    }

    @PostMapping("/get_students")
    public List<Object> findallStudents(@RequestBody SubjectInfo s ){
        return admin_service.get_student_info(s.getSemester() , s.getShift());
    }

    @PostMapping("/get_sub_details")
    public AdminSendDetails findSubjectALLDetails(@RequestBody SubjectInfo s ){
        Optional<Subject> sub = subjectRepo.findByShort_name(s.getSub_name());


        String obj = teacherRepo.findTeacherBySemAndShiftAndSubject(s.getSub_name() , s.getSemester() , s.getShift());
        Optional<Teacher> teacher  = teacherRepo.findByUserEmail(obj.toString());
        List<Object> objs =  admin_service.get_sub_all_details(s.getSub_name() , s.getSemester() , s.getShift());
        AdminSendDetails ad = new AdminSendDetails();
        ad.setStudent(objs);
        ad.setSubject(sub.get());
        ad.setTeacher_email(obj);
        ad.setTeacher_name(teacher.get().getUser().getName());


        return  ad;
    }

    @RequestMapping("/signIn")
    public String signup(@RequestBody LoginDetails loginDetails){

        Optional<Admin> admin = adminRepo.findByEmail(loginDetails.getEmail());
        if(admin.isPresent()){
            System.out.println(admin.get().toString());
            if(admin.get().getPassword().equals(loginDetails.getPassword())){
                return "success";
            }
            else{
                return "invalid";
            }
        }else {
            return "invalid";
        }
    }

    @RequestMapping("/add_classroom")
    public String add_subject_to_classroom(@RequestBody SubjectInfo s){

        Optional<Subject> sub = subjectRepo.findByShort_name(s.getSub_name());


        Optional<Subject> subject = subjectRepo.findByShort_name(s.getSub_name());


        Optional<Teacher> teacher = teacherRepo.findByUserEmail(s.getTeacher_email());

        Optional<Class> aclass = classRepo.findBySem_faculty_shift(s.getSemester(), s.getShift());

//        Optional<Subject> is_sub = classSubjectRepo.findBySubject(aclass.get());
//
//        if(is_sub.isPresent()){
//
//            return  "Already present";
//        }
        Optional<ClassSubjects> op_cs = classSubjectRepo.isSubjecPresent(aclass.get().getClass_id(),subject.get().getSubject_id());
        if(op_cs.isEmpty()) {


            if (subject.isPresent() && teacher.isPresent() && aclass.isPresent()) {
                ClassSubjects cs = new ClassSubjects();
                cs.setAClass(aclass.get());
                cs.setSubject(subject.get());
                classSubjectRepo.save(cs);

                Optional<Admin_assigned_Users> users = admin_service.findByEmail(s.getTeacher_email());
                TeacherSubjects ts = new TeacherSubjects();
                ts.setAClass(aclass.get());
                ts.setUsers(users.get());
                ts.setSubject(subject.get());
                teacherSubjectRepo.save(ts);

                return "success";
            }
        }else{
            return "Already added";
        }

    return "Fail" ;

    }
}
