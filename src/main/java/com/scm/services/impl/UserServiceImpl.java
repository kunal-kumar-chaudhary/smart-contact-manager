package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    // constructor autowiring the UserRepo
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // setting the default role of the user
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public User saveUser(User user) {
        // before saving the user, we will have to generate user id dynamically
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        // setting the encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // setting the user role
        user.setRoleList(List.of("ROLE_USER"));

        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
     return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found.."));
        // updating the user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        
        // saving the updated user
        return Optional.ofNullable(userRepo.save(user2));
    }

    @Override
    public void deleteUser(String id) {
    // fetching the user
    User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found.."));

    // deleting the user
    userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found.."));
        
        // if user2 is not null, then user exists and it will return true else false in other cases
        return user2 != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        // return true if there is a user with the given email
        // return false if there is no user with the given email
        return user != null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // we can implement our methods here

}
