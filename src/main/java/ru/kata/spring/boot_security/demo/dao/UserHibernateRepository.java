package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserHibernateRepository {


    public List<User> findAll();
    public void save(User user);
    public void saveRole(Role role);
    public User findById(Long id);
    public void deleteById(Long id);
    public User getUserByEmail(String email);
}
