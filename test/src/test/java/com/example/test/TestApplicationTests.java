package com.example.test;

import com.example.test.question.Question;
import com.example.test.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestApplicationTests {

    /**
     * @Autowired
     *
     * 객체를 주입하기 위해 사용하는 스프링의 애너테이션이다. 객체를 주입하는 방식에는 @Autowired 외에 Setter 또는 생성자를 사용하는 방식이 있다.
     * 순환참조 문제와 같은 이유로 @Autowired 보다는 생성자를 통한 객체 주입방식이 권장된다.
     * 하지만 테스트 코드의 경우에는 생성자를 통한 객체의 주입이 불가능하므로 테스트 코드 작성시에만
     * @Autowired를 사용하고 실제 코드 작성시에는 생성자를 통한 객체 주입방식을 사용하겠다.
     * */
    @Autowired
    private QuestionRepository qRepo;

    @Test
    void testJpa() {
        // given

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

        // when
        List<Question> all = this.qRepo.findAll();
        Question q = all.get(0);
        Optional<Question> oq = this.qRepo.findById(1);
        if(oq.isPresent()){
            oq.get();

        }

        Question qq = this.qRepo.findBySubject("sbb가 무엇인가요?");



        // then

        assertEquals(2, all.size());
        assertEquals("sbb가 무엇인가요?", q.getSubject());
        assertEquals(1, qq.getId());



        // 데이터 수정..
        //  id가 있으면 update, id없으면 insert
        Optional<Question> oq3 = this.qRepo.findById(1);
        assertTrue(oq3.isPresent());
        Question q3 = oq3.get();
        q3.setSubject("수정된 제목");
        this.qRepo.save(q3);  // update  ? save ?


        // 데이터 삭제하기..
        qRepo.deleteById(2);

    }

}
