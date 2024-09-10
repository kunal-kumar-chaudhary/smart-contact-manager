package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
 
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

    private boolean enabled=true;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    @Enumerated(value = EnumType.STRING)
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

    // we will need to make it element collection becuase we cannpt sabe the list directly to the database
    // we will need to save the list as a separate table
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // we are not using roles for now
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(SimpleGrantedAuthority::new).toList();
        return roles;
    }

    @Override
    public String getUsername() {
        // our email id is our username for this project
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
