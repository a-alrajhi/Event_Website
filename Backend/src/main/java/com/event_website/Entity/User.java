package com.event_website.Entity;

import com.event_website.Request.RegisterRequest;
import com.event_website.Request.UpdateUserRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    public User(RegisterRequest registerRequest) {
        this.email = registerRequest.getEmail();
        this.password = registerRequest.getPassword();
        this.phoneNumber = registerRequest.getPhoneNumber();
        this.name = registerRequest.getName();
    }
    public User(UpdateUserRequest updateUserRequest) {
        this.email = updateUserRequest.getEmail();
        this.password = updateUserRequest.getPassword();
        this.phoneNumber = updateUserRequest.getPhoneNumber();
        this.name = updateUserRequest.getName();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;
}