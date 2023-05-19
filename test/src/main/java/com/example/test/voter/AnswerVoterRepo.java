package com.example.test.voter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerVoterRepo extends JpaRepository<AnswerVoter,Integer> {

    Optional<AnswerVoter> findByAnswer_idAndVoter_id(Integer answer_id, Long voter_id);
}
