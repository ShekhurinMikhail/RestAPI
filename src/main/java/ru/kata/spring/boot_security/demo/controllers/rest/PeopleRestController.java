package ru.kata.spring.boot_security.demo.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.ExceptionHandling.NoSuchPeopleException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PeopleRestController {
    private final UserService userService;
    private final RoleService roleService;

    public PeopleRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long id) {
        User user = userService.showUser(id);
        if (user == null) {
            throw new NoSuchPeopleException("There is no person with ID = " + id + " in DataBase");
        }
        return user;
    }

    @PostMapping()
    public User saveUser(@RequestBody User user) {
        if (user.getRoles().isEmpty()) {
            user.setRoles(roleService.findRoleByName("ROLE_USER"));
        }
        userService.saveUser(user);
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        if (user.getRoles().isEmpty()) {
            user.setRoles(roleService.findRoleByName("ROLE_USER"));
        }
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable(value = "id") Long id) {
        if (userService.showUser(id) == null) {
            throw new NoSuchPeopleException("There is no person with ID = " + id + " in DataBase");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
