package com.ead.authuser.Controller;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.dtos.UserView;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            SpecificationTemplate.UserSpec spec,
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserDTO> user = userService.findAll(spec, pageable);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable(value = "userId") UUID userId) {
        UserDTO user = userService.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> delete(@PathVariable(value = "userId") UUID userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> update(@PathVariable(value = "userId") UUID userId,
                                          @Validated(UserView.UserPut.class)
                                          @JsonView(UserView.UserPut.class) @RequestBody UserDTO userDTO) {
        UserDTO update = userService.update(userId, userDTO);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}/password")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                  @Validated(UserView.PasswordPut.class)
                                                  @JsonView(UserView.PasswordPut.class) @RequestBody UserDTO userDTO) {
        UserDTO update = userService.updatePassword(userId, userDTO);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}/image")
    public ResponseEntity<UserDTO> updateImage(@PathVariable(value = "userId") UUID userId,
                                               @Validated(UserView.ImagePut.class)
                                               @JsonView(UserView.ImagePut.class) @RequestBody UserDTO userDTO) {
        UserDTO update = userService.updateImage(userId, userDTO);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

}
