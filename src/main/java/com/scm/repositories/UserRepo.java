package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.User;

public interface UserRepo extends JpaRepository<User,String> {

}
