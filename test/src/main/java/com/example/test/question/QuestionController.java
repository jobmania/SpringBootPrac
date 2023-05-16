package com.example.test.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository qRepo;

    @GetMapping("question/list")
    public String list(Model model) {
        model.addAttribute("qList", qRepo.findAll());
        return "question_list";
    }


}
