package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findRolesByNames(String[] selectedRoles);
    Set<Role> findRoleByName(String role);
}
