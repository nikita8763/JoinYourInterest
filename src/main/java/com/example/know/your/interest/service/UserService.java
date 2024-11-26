package com.example.know.your.interest.service;


import com.example.know.your.interest.model.User;
import com.example.know.your.interest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(Long id) {
            super("User with ID " + id + " not found");
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
