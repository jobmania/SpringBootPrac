package com.example.test.question;

import com.example.test.answer.AnswerService;
import com.example.test.dto.AnswerForm;
import com.example.test.dto.QuestionForm;
import com.example.test.user.SiteUser;
import com.example.test.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Question question = qService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()") // 비로그인 접근시 로그인 화면으로 보냄.
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



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.qService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }






    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {


        if (bindingResult.hasErrors()) {
            return "question_form";
        }


        Question question = qService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        question = qService.modifyQuestion(question, questionForm);


        return "redirect:/question/detail/"+id;
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id, Principal principal){
        Question question = qService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        qService.deleteQuestion(question);
        return "redirect:/question/list";

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = qService.getQuestion(id);
        SiteUser siteUser = uService.getUser(principal.getName());
        qService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

}
