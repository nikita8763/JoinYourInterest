package com.example.know.your.interest.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String interest;
    private int maxSeats;
    private int currentSeats;
    @Column(name = "game")
    private String game;
    private String time;
    private int maxParticipants;
    @Column(name = "current_participants")
    private int currentParticipants;
    private  boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "poll", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PollParticipant> participants = new ArrayList<>();

}
