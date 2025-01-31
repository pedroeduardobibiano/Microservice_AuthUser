package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.exceptions.DataIntegrityViolationException;
import com.ead.authuser.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Specification<UserModel> spec, Pageable pageable) {
        Page<UserModel> usersPage = userRepository.findAll(spec, pageable);
        return usersPage.map(UserDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(UUID userId) {
        UserModel userModel = getUserModel(userId);
        return new UserDTO(userModel);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public UserDTO save(UserDTO userDTO) {
        viewIfUsernameAlreadyExists(userDTO);
        viewIfEmailAlreadyExists(userDTO);
        UserModel userModel = new UserModel();
        extracted(userDTO, userModel);
        return new UserDTO(userRepository.save(userModel));

    }

    @Transactional
    public UserDTO update(UUID userId, UserDTO userDTO) {
        UserModel userModel = getUserModel(userId);
        userModel.setFullName(userDTO.getFullName());
        userModel.setPhoneNumber(userDTO.getPhoneNumber());
        userModel.setCpf(userDTO.getCpf());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return new UserDTO(userRepository.save(userModel));
    }

    @Transactional
    public UserDTO updatePassword(UUID userId, UserDTO userDTO) {
        UserModel userModel = getUserModel(userId);
        if (!userModel.getPassword().equals(userDTO.getOldPassword())) {
            throw new DataIntegrityViolationException("Passwords do not match");
        }
        userModel.setPassword(userDTO.getPassword());
        return new UserDTO(userRepository.save(userModel));
    }

    @Override
    public UserDTO updateImage(UUID userId, UserDTO userDTO) {
        UserModel userModel = getUserModel(userId);
        userModel.setImageUrl(userDTO.getImageUrl());
        return new UserDTO(userRepository.save(userModel));
    }


    private void viewIfUsernameAlreadyExists(UserDTO userDTO) {
        userRepository.findByUserName(userDTO.getUsername())
                .ifPresent(user -> {
                    System.out.println("Found username: " + user.getUserName());
                    throw new DataIntegrityViolationException("Username already exists!");
                });
    }

    private void viewIfEmailAlreadyExists(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    System.out.println("Found email: " + user.getEmail());
                    throw new DataIntegrityViolationException("Email already exists!");
                });
    }

    private static void extracted(UserDTO userDTO, UserModel userModel) {
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    }

    private UserModel getUserModel(UUID userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
