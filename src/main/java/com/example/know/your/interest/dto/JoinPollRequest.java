package com.example.know.your.interest.dto;

import lombok.Data;

@Data
public class JoinPollRequest {
    private Long pollId;
    private Long userId;
}
