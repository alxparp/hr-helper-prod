package com.hrsol.helper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponseBody {

    private String msg;
    private Page<LetterDTO> result;
    private List<Integer> pageNumbers;

}
