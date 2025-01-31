package com.ead.authuser.Controller;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.UserView;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> registerUser(@JsonView(UserView.RegistrationPost.class)
                                                @Validated(UserView.RegistrationPost.class)
                                                @RequestBody UserDTO userDTO
    ) {
        UserDTO user = userService.save(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}
