package com.example.know.your.interest.dto;

import com.example.know.your.interest.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}