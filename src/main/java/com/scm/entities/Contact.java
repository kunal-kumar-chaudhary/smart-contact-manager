package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contact {
 
    @Id
    private String id;

    private String name;
    private String email;
    private String address;
    private String picture;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean favorite=false;

    // below, socialLinks can hold reference to any object that implements the List interface.
    // private List<String> socialLinks = new ArrayList<>();

    private String websiteLink;
    private String linkedInLink;

    // many contacts can belong to one user
    // so we will do many to one mapping

    @ManyToOne
    private User user;

    
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();

}
