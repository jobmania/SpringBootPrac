package com.example.test.voter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionVoterRepo extends JpaRepository<QuestionVoter,Integer> {
    Optional<QuestionVoter> findByQuestion_idAndVoter_id(Integer quest_id, Long voter_id);

}
