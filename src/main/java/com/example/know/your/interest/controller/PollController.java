package com.example.know.your.interest.controller;

import com.example.know.your.interest.dto.CreatePollRequest;
import com.example.know.your.interest.dto.PollResponseDTO;
import com.example.know.your.interest.model.Poll;
import com.example.know.your.interest.model.User;
import com.example.know.your.interest.service.PollService;
import com.example.know.your.interest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    @PostMapping
    public PollResponseDTO createPoll(@RequestBody CreatePollRequest createPollRequest, @RequestParam Long userId) {
        System.out.println("Received userId: " + userId);

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User with ID " + userId + " does not exist.");
        }

        System.out.println("User found: " + user.getName());

        Poll poll = pollService.createPoll(createPollRequest, userId);

        return pollService.convertToPollResponseDTO(poll);
    }

    @GetMapping("/{id}")
    public PollResponseDTO getPoll(@PathVariable Long id) {
        Poll poll = pollService.getPollById(id);
        return pollService.convertToPollResponseDTO(poll);
    }


    @GetMapping
    public List<PollResponseDTO> getAllPolls() {
        return pollService.getAllPolls();
    }

    @PostMapping("/{pollId}/join")
    public String joinPoll(@PathVariable Long pollId, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        boolean success = pollService.joinPoll(pollId, user);
        return success ? "Successfully joined the poll!" : "Unable to join the poll.";
    }


}