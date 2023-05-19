package com.example.test.answer;

import com.example.test.exception.DataNotFoundException;
import com.example.test.question.Question;
import com.example.test.user.SiteUser;
import com.example.test.voter.AnswerVoter;
import com.example.test.voter.AnswerVoterRepo;
import com.example.test.voter.QuestionVoter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository aRepo;
    private final AnswerVoterRepo avRepo;

    public void create(Question question, String content, SiteUser author){

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);
        aRepo.save(answer);
    }


    public Answer getAnswer(Integer id){
        Optional<Answer> answer = aRepo.findById(id);
        if(answer.isPresent()){
            return answer.get();
        }else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modifyAnswer(Answer answer, String content){
       answer.setContent(content);
       answer.setModifyDate(LocalDateTime.now());
        aRepo.save(answer);
    }

    public void deleteAnswer(Answer answer){
        aRepo.delete(answer);

    }

    public void vote(Answer answer, SiteUser siteUser){
        Optional<AnswerVoter> findAV = avRepo.findByAnswer_idAndVoter_id(answer.getId(), siteUser.getId());
        if(findAV.isPresent()){
            // 한번 좋아요 눌럿다면 다시 누를시 제거!
            avRepo.delete(findAV.get());
            return;
        }
        AnswerVoter av = new AnswerVoter();
        av.setAnswer(answer);
        av.setVoter(siteUser);
        avRepo.save(av);
    }


}
