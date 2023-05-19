package com.example.test.question;

import com.example.test.dto.QuestionForm;
import com.example.test.exception.DataNotFoundException;
import com.example.test.user.SiteUser;
import com.example.test.voter.QuestionVoter;
import com.example.test.voter.QuestionVoterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository qRepo;
    private final QuestionVoterRepo qvRepo;

    public Page<Question> getList(int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending()); // 최신순으로 desc

        return qRepo.findAll(pageable);
    }

    public Question getQuestion(Integer id) throws DataNotFoundException {
        Optional<Question> question = this.qRepo.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }


    public void save(String subject, String content, SiteUser author) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(author);
        qRepo.save(question);
    }

    public Question modifyQuestion(Question question, QuestionForm questionForm) {
        question.setSubject(questionForm.getSubject());
        question.setContent(questionForm.getContent());
        question.setModifyDate(LocalDateTime.now());
        qRepo.save(question);
        return question;
    }

    public void deleteQuestion(Question question){
        qRepo.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        Optional<QuestionVoter> findQV = qvRepo.findByQuestion_idAndVoter_id(question.getId(), siteUser.getId());
        if(findQV.isPresent()){
            // 한번 좋아요 눌럿다면 다시 누를시 제거!
            qvRepo.delete(findQV.get());
            return;
        }
        QuestionVoter qv = new QuestionVoter();
        qv.setQuestion(question);
        qv.setVoter(siteUser);
        qvRepo.save(qv);
    }

    public boolean checkVote(Question question, SiteUser siteUser){
        Optional<QuestionVoter> findQV = qvRepo.findByQuestion_idAndVoter_id(question.getId(), siteUser.getId());
        return findQV.isPresent();
    }


}

