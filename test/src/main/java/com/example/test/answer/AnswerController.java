package com.example.test.answer;

import com.example.test.dto.AnswerForm;
import com.example.test.question.Question;
import com.example.test.question.QuestionService;
import com.example.test.user.SiteUser;
import com.example.test.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService qService;
    private final AnswerService aService;
    private final UserService uService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid AnswerForm answerForm, BindingResult result
                                , Principal principal) {
        Question question = qService.getQuestion(id);
        SiteUser author = uService.getUser(principal.getName());

        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError e : list) {
                log.info("error={}",e.getDefaultMessage());
            }

            model.addAttribute("question", question);
            return "question_detail";
        }


        // TODO: 답변을 저장한다.
        aService.create(question,answerForm.getContent(),author);
        return String.format("redirect:/question/detail/%s", id);
    }
}
