package com.arun.ag_backend.Dto.HelperDTO;

import com.arun.ag_backend.Entities.Student;
import com.arun.ag_backend.Entities.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSendDetails {

    private Subject subject;
    private Object teacher_email;
    private List<Object> student;
}
