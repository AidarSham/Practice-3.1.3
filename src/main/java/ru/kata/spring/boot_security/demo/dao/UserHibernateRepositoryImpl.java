package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserHibernateRepositoryImpl implements UserHibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public UserHibernateRepositoryImpl(EntityManager entityManager) {
        this.entityManager = this.entityManager;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u join fetch u.roles", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        User merge = entityManager.merge(user);
        entityManager.persist(merge);
    }

    @Override
    public void saveRole(Role role) {
        Role merge = entityManager.merge(role);
        entityManager.persist(merge);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteById(Long id) {
        String hql = "delete from User where id = :ID";
        Query query = entityManager.createQuery(hql);
        query.setParameter("ID", id);
        query.executeUpdate();
    }

    @Override
    public User getUserByEmail(String email) {
        String hql = "SELECT u FROM User u join fetch u.roles WHERE u.email = :email";
        Query query = entityManager.createQuery(hql);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
