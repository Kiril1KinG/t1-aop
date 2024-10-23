package com.example.demo.service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();
    private Long currentId = 1L;

    public User createUser(UserDto dto) {
        User user = new User(currentId++, dto.getName(), dto.getEmail(), dto.getPhone(), LocalDateTime.now());
        users.put(user.getId(), user);
        return user;
    }

    public User getUserById(Long userId) {
        return users.get(userId);
    }

    public void updateUser(Long userId, UserDto dto) {
        if (!users.containsKey(userId)){
            throw new UserNotFoundException(String.format("User with id %d not found", userId));
        }

        User user = users.get(userId);

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
    }

    public void deleteUser(Long userId) {
        if (!users.containsKey(userId)){
            throw new UserNotFoundException(String.format("User with id %d not found", userId));
        }
        users.remove(userId);
    }
}

