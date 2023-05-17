package com.example.test.dto;

import com.example.test.question.Question;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AnswerForm {

    @NotBlank(message = "내용은 필수항목입니다.")
    private String content;
}
