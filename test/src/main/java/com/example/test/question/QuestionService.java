package com.example.test.question;

import com.example.test.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository qRepo;

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


    public void save(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        qRepo.save(question);
    }
}

