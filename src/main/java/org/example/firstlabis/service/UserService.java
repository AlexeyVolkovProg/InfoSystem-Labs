package org.example.firstlabis.service;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.model.security.User;
import org.example.firstlabis.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // todo проверить на существование user, чтобы избежать повторных записей
    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserByUsername (String username){
        return userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }

    public boolean isUserExist(String username){
        return userRepository.existsByUsername(username);
    }
}
