package com.example.know.your.interest.controller;

import com.example.know.your.interest.dto.CreatePollRequest;
import com.example.know.your.interest.dto.PollResponseDTO;
import com.example.know.your.interest.model.Poll;
import com.example.know.your.interest.model.User;
import com.example.know.your.interest.service.PollService;
import com.example.know.your.interest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PollResponseDTO> createPoll(@RequestBody CreatePollRequest createPollRequest, @RequestParam Long userId) {
        System.out.println("Received userId: " + userId);

        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(404).body(null);
            }

            Poll poll = pollService.createPoll(createPollRequest, userId);
            PollResponseDTO response = pollService.convertToPollResponseDTO(poll);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollResponseDTO> getPoll(@PathVariable Long id) {
        try {
            Poll poll = pollService.getPollById(id);
            if (poll == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(pollService.convertToPollResponseDTO(poll));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<PollResponseDTO>> getAllPolls() {
        try {
            List<PollResponseDTO> polls = pollService.getAllPolls();
            return ResponseEntity.ok(polls);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/{pollId}/join")
    public ResponseEntity<String> joinPoll(@PathVariable Long pollId, @RequestParam Long userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found.");
            }

            boolean success = pollService.joinPoll(pollId, user);
            if (success) {
                return ResponseEntity.ok("Successfully joined the poll!");
            } else {
                return ResponseEntity.badRequest().body("Unable to join the poll.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while joining the poll."); 
        }
    }


}