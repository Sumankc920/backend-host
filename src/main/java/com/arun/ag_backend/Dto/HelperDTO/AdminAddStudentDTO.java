package com.arun.ag_backend.Dto.HelperDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAddStudentDTO {

    private String email;
    private String roll;
    private String user_role;
    private String shift;
    private String semester;
}
