package com.ead.authuser.services;

import com.ead.authuser.models.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    UserModel findById(UUID userId);

    void deleteById(UUID userId);
}
