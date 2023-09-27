package com.arun.ag_backend.Dto.HelperDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherGetAttendance {

    private String sub_name;
    private LocalDate date;
    int class_id;
    int roll;
}
