package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.exceptions.DataIntegrityViolationException;
import com.ead.authuser.services.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findById(UUID userId) {
        return getUserModel(userId);
    }

    @Override
    public void deleteById(UUID userId) {
        try {
            if (!userRepository.existsById(userId)) {
                throw new EntityNotFoundException("User not found");
            }
            userRepository.deleteById(userId);
        } catch (RuntimeException e) {
            throw new DataIntegrityViolationException("IntegrityViolationException");
        }
    }

    private UserModel getUserModel(UUID userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
