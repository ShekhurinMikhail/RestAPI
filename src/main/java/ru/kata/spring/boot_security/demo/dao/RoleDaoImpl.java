package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> findRolesByNames(String[] selectedRoles) {
        List<String> selectedRolesList = Arrays.asList(selectedRoles);
        return entityManager
                .createQuery("select r from Role r where r.role in :selectedRolesList", Role.class)
                .setParameter("selectedRolesList", selectedRolesList)
                .getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Set<Role> findRoleByName(String role) {
        return entityManager.createQuery("select r from Role r where r.role=:role", Role.class)
                .setParameter("role", role)
                .getResultStream().collect(Collectors.toSet());
    }
}
