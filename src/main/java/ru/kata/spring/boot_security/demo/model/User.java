package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    // Set уникальные значения
    private Set<Role> roles;

//    Получаем роли в виде String
    public String getAllRolesString() {
        String allRolesString = new String();
        for (Role role: roles) {
            allRolesString += role.toString() + " ";
        }

        return allRolesString;
    }

    public User() {

    }

    public User(Long id, String username, String lastName, int age, String password, String email, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User(String username, String lastName, int age, String password, String email, Set<Role> roles) {
        this.username = username;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}