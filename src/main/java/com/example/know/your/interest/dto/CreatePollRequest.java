package com.example.know.your.interest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreatePollRequest {
    private String title;
    private String interest;
    private int maxSeats;
    private String game;
    private String time;
    private int maxParticipants;
    private Long userId;
}
