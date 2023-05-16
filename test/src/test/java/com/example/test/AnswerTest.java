package com.example.test;

import com.example.test.answer.Answer;
import com.example.test.question.Question;
import com.example.test.answer.AnswerRepository;
import com.example.test.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnswerTest {
    @Autowired
    private AnswerRepository aRepo;

    @Autowired
    private QuestionRepository qRepo;

    @Test
    @DisplayName("답변 테스트")
    void answerTest(){

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.qRepo.save(q1); // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.qRepo.save(q2);  // 두번째 질문 저장


        Optional<Question> oq = this.qRepo.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.aRepo.save(a);


        Optional<Answer> oa = this.aRepo.findById(1);
        assertTrue(oa.isPresent());
        Answer a1 = oa.get();
        assertEquals(2, a1.getQuestion().getId());

    }



}
