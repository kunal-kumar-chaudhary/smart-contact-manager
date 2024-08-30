package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
 
    @Id
    private String userId;

    @Column(name="user_name", nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(columnDefinition = "TEXT")
    private String profilePic;

    private String phoneNumber;

    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    private Providers provider = Providers.SELF;
    private String providerUserId;
 
    // a user has multiple contacts
    // we will do one to many mapping here
    // mappedBy is used to tell hibernate that this field is mapped by user field in Contact class
    // uni-directional mapping
    // if we delete a user, all contacts of that user will be deleted
    // if we delete a contact, user will not be deleted
    // LAZY - contacts will be fetched only when we explicitly fetch contacts
    // EAGER - contacts will be fetched when we fetch user

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
 
} 
