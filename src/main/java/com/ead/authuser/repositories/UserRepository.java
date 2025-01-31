package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    @Query(value = "SELECT userName FROM tb_users WHERE UPPER(userName) = UPPER(:userName)", nativeQuery = true)
    Optional<UserProjection> findByUserName(String userName);

    @Query(value = "SELECT email FROM tb_users WHERE UPPER(email) = UPPER(:email)", nativeQuery = true)
    Optional<UserProjection> findByEmail(String email);


}
