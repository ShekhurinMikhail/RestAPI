package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> findRolesByNames(String[] selectedRoles) {
        return roleDao.findRolesByNames(selectedRoles);
    }

    @Override
    public Set<Role> findRoleByName(String role) {
        return roleDao.findRoleByName(role);
    }
}
