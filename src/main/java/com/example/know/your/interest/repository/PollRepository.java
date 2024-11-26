package com.example.know.your.interest.repository;

import com.example.know.your.interest.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long> {

    List<Poll> findByIsOpenTrue();

    List<Poll> findByCreatedById(Long userId);

    Optional<Poll> findById(Long id);

    @Query("SELECT p FROM Poll p LEFT JOIN FETCH p.participants WHERE p.id = :pollId")
    Poll findPollWithParticipants(@Param("pollId") Long pollId);

}
