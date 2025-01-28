package com.ead.authuser.Controller;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> user = userService.findAll();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserModel> findById(@PathVariable(value = "userId") UUID userId) {
        UserModel user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<UserModel> delete(@PathVariable(value = "userId") UUID userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
