package com.arun.ag_backend.Controller;

import com.arun.ag_backend.Dto.HelperDTO.TeacherGetAttendance;
import com.arun.ag_backend.Entities.Attendance;
import com.arun.ag_backend.Entities.Class;
import com.arun.ag_backend.Entities.Student;
import com.arun.ag_backend.Entities.Subject;
import com.arun.ag_backend.Services.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "${cross.origin.url}", allowedHeaders = "Authorization")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @RequestMapping("/classes")
    public String get_teaching_details(Principal principal){
        List<Class> teachingDetailsList = teacherService.get_teacher_classes(principal.getName());

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(teachingDetailsList);
            return json;
        } catch (Exception e) {
            // Handle any potential exceptions
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/classes/subject/{class_id}")
    public String get_subject(@PathVariable("class_id") int classId, Principal principal){
        System.out.println(principal.getName() + " " + classId);
        Subject subject = teacherService.get_teacher_subject(principal.getName() , classId);
//        System.out.println(principal.getName() + " " + classId);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(subject);

            return json;
        } catch (Exception e) {
            // Handle any potential exceptions
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping("/get_attendance")
    public List<Object> get_attendance(@RequestBody TeacherGetAttendance tbody){
        Optional<List<Object>> stds = teacherService.findAttendance(tbody.getSub_name() , tbody.getDate() , tbody.getClass_id());

        if(stds.isPresent() && !stds.get().isEmpty()){
            return  stds.get();
        }else {
            return Collections.singletonList("There was no class in this date");
        }
    }
}
