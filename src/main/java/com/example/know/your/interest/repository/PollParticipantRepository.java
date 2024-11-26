package com.example.know.your.interest.repository;

import com.example.know.your.interest.model.PollParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollParticipantRepository extends JpaRepository<PollParticipant, Long> {
    boolean existsByPollIdAndUserId(Long pollId, Long userId);
    List<PollParticipant> findByPollId(Long pollId);
}
