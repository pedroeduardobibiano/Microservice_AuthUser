package com.ead.authuser.dtos;

import com.ead.authuser.dtos.validation.UsernameConstraint;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements UserView {

    private UUID userId;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @UsernameConstraint(groups = UserView.RegistrationPost.class)
    @Size(min = 4, max = 50, message = "minimo 4 caracteres e maximo 50",
            groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    private String username;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email
    @Size(min = 6, max = 70, message = "minimo 6 caracteres e maximo 70",
            groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    private String email;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max = 20, message = "minimo 6 caracteres e maximo 20",
            groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 6, max = 20, message = "minimo 6 caracteres e maximo 20"
            , groups = UserView.PasswordPut.class)
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @Size(min = 6, max = 100, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    private UserStatus userStatus;
    private UserType userType;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    @Pattern(regexp = "(^(\\d{3}.\\d{3}.\\d{3}-\\d{2})|(\\d{11})$)", message = "Cpf Invalido",
            groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @Size(min = 4, max = 100, message = "minimo 4 caracteres e maximo 100",
            groups = UserView.ImagePut.class)
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;


    public UserDTO(UserModel userModel) {
        userId = userModel.getUserId();
        username = userModel.getUsername();
        email = userModel.getEmail();
        password = userModel.getPassword();
        fullName = userModel.getFullName();
        userStatus = userModel.getUserStatus();
        userType = userModel.getUserType();
        phoneNumber = userModel.getPhoneNumber();
        cpf = userModel.getCpf();
        imageUrl = userModel.getImageUrl();
        creationDate = LocalDateTime.now();
        lastUpdateDate = LocalDateTime.now();
    }


}
