package com.briannalytical.wealthcore.Model.Entity;

import com.briannalytical.wealthcore.Model.Enum.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


    public User() {}

    public User(Long id, String username, String password, String email, UserRole role, boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isEnabled = isEnabled;
    }

    // getters
    public Long getId() {return id;}

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public String getEmail() {return email;}

    public String getRole() {return role;}

    public boolean isEnabled() {return isEnabled;}


    // setters
    public void setId(Long id) {this.id = id;}

    public void setUsername(String username) {this.username = username;}

    public void setPassword(String password) {this.password = password;}

    public void setEmail(String email) {this.email = email;}

    public void setRole(String role) {this.role = role;}

    public void setEnabled(boolean enabled) {isEnabled = enabled;}
}