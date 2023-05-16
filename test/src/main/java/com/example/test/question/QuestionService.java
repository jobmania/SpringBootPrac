package com.example.test.question;

import com.example.test.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository qRepo;

    public List<Question> getList(){
        return this.qRepo.findAll();
    }

    public Question getQuestion(Integer id) throws DataNotFoundException {
        Optional<Question> question = this.qRepo.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }



}

