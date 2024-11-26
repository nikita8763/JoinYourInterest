package com.example.know.your.interest.dto;

import com.example.know.your.interest.model.Poll;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollResponseDTO {
    private Long id;
    private String game;
    private String time;
    private int maxParticipants;
    private List<UserDTO> participants;
    private UserDTO createdBy;

    public static PollResponseDTO fromPoll(Poll poll) {
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
