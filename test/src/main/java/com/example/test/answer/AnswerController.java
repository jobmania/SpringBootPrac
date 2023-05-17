package com.example.test.answer;

import com.example.test.question.Question;
import com.example.test.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService qService;
    private final AnswerService aService;
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @RequestParam String content) {
        Question question = this.qService.getQuestion(id);
        // TODO: 답변을 저장한다.
        aService.create(question,content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
