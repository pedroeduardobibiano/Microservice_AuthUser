package com.ead.authuser.services;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface UserService {
    Page<UserDTO> findAll(Specification<UserModel> spec, Pageable pageable);

    UserDTO findById(UUID userId);

    void deleteById(UUID userId);

    UserDTO save(UserDTO userDTO);

    UserDTO update(UUID userId, UserDTO userDTO);

    UserDTO updatePassword(UUID userId, UserDTO userDTO);

    UserDTO updateImage(UUID userId, UserDTO userDTO);


}
