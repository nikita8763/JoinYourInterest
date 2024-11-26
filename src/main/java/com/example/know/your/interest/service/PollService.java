package com.example.know.your.interest.service;

import com.example.know.your.interest.dto.CreatePollRequest;
import com.example.know.your.interest.dto.PollResponseDTO;
import com.example.know.your.interest.dto.UserDTO;
import com.example.know.your.interest.model.Poll;
import com.example.know.your.interest.model.PollParticipant;
import com.example.know.your.interest.model.User;
import com.example.know.your.interest.repository.PollParticipantRepository;
import com.example.know.your.interest.repository.PollRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollParticipantRepository pollParticipantRepository;

    @Autowired
    private UserService userService;

    public class PollNotFoundException extends RuntimeException {
        public PollNotFoundException(Long pollId) {
            super("Poll with ID " + pollId + " not found");
        }
    }

    public Poll createPoll(CreatePollRequest request, Long userId) {
        User user = userService.getUserById(userId);

        Poll poll = new Poll();
        poll.setGame(request.getGame());
        poll.setTime(request.getTime());
        poll.setMaxParticipants(request.getMaxParticipants());
        poll.setCreatedBy(user);
        poll.setCurrentParticipants(0);
        poll.setOpen(true);

        return pollRepository.save(poll);
    }

    public Poll getPollById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poll not found with id " + id));
    }

    public List<PollResponseDTO> getAllPolls() {
        return pollRepository.findAll()
                .stream()
                .map(this::convertToPollResponseDTO)
                .collect(Collectors.toList());
    }

    public boolean joinPoll(Long pollId, User user) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new PollNotFoundException(pollId));

        if (pollParticipantRepository.existsByPollIdAndUserId(pollId, user.getId()) ||
                poll.getCurrentParticipants() >= poll.getMaxParticipants()) {
            return false;
        }

        PollParticipant participant = new PollParticipant();
        participant.setPoll(poll);
        participant.setUser(user);
        pollParticipantRepository.save(participant);

        poll.setCurrentParticipants(poll.getCurrentParticipants() + 1);
        if (poll.getCurrentParticipants() == poll.getMaxParticipants()) {
            poll.setOpen(false);
        }
        pollRepository.save(poll);

        return true;
    }

    public PollResponseDTO convertToPollResponseDTO(Poll poll) {
        List<UserDTO> participants = poll.getParticipants().stream()
                .map(participant -> new UserDTO(participant.getUser()))
                .collect(Collectors.toList());

        UserDTO createdBy = new UserDTO(poll.getCreatedBy());

        return new PollResponseDTO(
                poll.getId(),
                poll.getGame(),
                poll.getTime(),
                poll.getMaxParticipants(),
                participants,
                createdBy
        );
    }
}