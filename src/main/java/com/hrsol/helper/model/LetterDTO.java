package com.hrsol.helper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.sql.Date;

@Data
@AllArgsConstructor
@Builder
public class LetterDTO {

    private Long id;
    private String name;
    private Date dueDate;
    private String city;
    private String letterStatus;

}
