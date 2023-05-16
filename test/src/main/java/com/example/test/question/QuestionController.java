package com.example.test.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService qService;

    @GetMapping("question/list")
    public String list(Model model) {
        model.addAttribute("qList", qService.getList());
        return "question_list";
    }


    @GetMapping( "question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.qService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }


}
