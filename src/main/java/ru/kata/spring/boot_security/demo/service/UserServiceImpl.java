package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserHibernateRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserHibernateRepository userHibernateRepository;

    @Autowired
    public UserServiceImpl(UserHibernateRepository userHibernateRepository) {
        this.userHibernateRepository = userHibernateRepository;
    }

    @PostConstruct
    private void construct(){
        Set<Role> roleAdmin = new HashSet<>();
        Role role = new Role("ROLE_ADMIN");
        roleAdmin.add(role);
        this.save(new User( "admin", "adminov", 33, "$2a$12$vuYKNMeCKao28klvEkTJf.VGZomEgiYiqF.0r/eMbEgOzn/WAJyl2", "admin@mail.com", roleAdmin));

        Set<Role> roleUser = new HashSet<>();
        Role roleU = new Role("ROLE_USER");
        roleUser.add(roleU);
        this.save(new User( "user", "userov", 45, "$2a$12$TXVvP33zPQVuFfqawFe9heyWenK9vxJ3i2/XXmIKJLsYuLZQb7R8i", "user@mail.com", roleUser));
    }

    public List<User> listAll(){
        return userHibernateRepository.findAll();
    }

    public void save(User user){
        userHibernateRepository.save(user);
    }

    public User get(Long id){
        return userHibernateRepository.findById(id);
    }

    public void delete(Long id) {
        userHibernateRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userHibernateRepository.getUserByEmail(email);
    }

}
