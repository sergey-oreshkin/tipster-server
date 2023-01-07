package com.home.tipster.users.repository;

import com.home.tipster.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity, String> {
}
