package com.example.test.question;

import com.example.test.answer.AnswerService;
import com.example.test.dto.AnswerForm;
import com.example.test.dto.QuestionForm;
import com.example.test.user.SiteUser;
import com.example.test.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    private final QuestionService qService;
    private final UserService uService;


    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("paging", qService.getList(page-1));
        return "question_list";
    }


    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.qService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(
            @Valid QuestionForm questionForm, BindingResult result, Principal principal) {
        /**BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. 만약 2개의 매개변수의 위치가 정확하지 않다면
         * @Valid만 적용이 되어 입력값 검증 실패 시 400 오류가 발생한다.
         * */
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError e : list) {
                log.info("error={}", e.getDefaultMessage());
            }
            return "question_form";
        }

        SiteUser author = uService.getUser(principal.getName());

        qService.save(questionForm.getSubject(), questionForm.getContent(), author);

        return "redirect:/question/list";
    }


}
