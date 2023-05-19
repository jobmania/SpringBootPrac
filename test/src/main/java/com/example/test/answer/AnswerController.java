package com.example.test.answer;

import com.example.test.dto.AnswerForm;
import com.example.test.question.Question;
import com.example.test.question.QuestionService;
import com.example.test.user.SiteUser;
import com.example.test.user.UserService;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyAnswer(@PathVariable("id") Integer id, Principal principal
                                , AnswerForm answerForm){
        Answer answer = aService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = aService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
       aService.modifyAnswer(answer, answerForm.getContent());
        return "redirect:/question/detail/"+answer.getQuestion().getId();
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id, Principal principal){
        Answer answer = aService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        aService.deleteAnswer(answer);
        return "redirect:/question/detail/"+answer.getQuestion().getId();

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = aService.getAnswer(id);
        SiteUser siteUser = uService.getUser(principal.getName());
        aService.vote(answer, siteUser);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

}
