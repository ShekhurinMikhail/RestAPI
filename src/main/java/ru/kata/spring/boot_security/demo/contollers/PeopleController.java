package ru.kata.spring.boot_security.demo.contollers;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class PeopleController {
    private final UserService userService;
    private final RoleService roleService;

    public PeopleController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUserInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findUserByEmail(userDetails.getUsername()));
        model.addAttribute("people", userService.getAllUsers());
        model.addAttribute("new_user", new User());
        return "/admin";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user, @RequestParam(value = "selectedRoles") String[] selectedRoles) {
        user.setRoles(roleService.findRolesByNames(selectedRoles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(params = "id")
    public String delete(@RequestParam(value = "id", required = false) long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable(value = "id") Long id, @RequestParam(value = "selectedRoles", required = false) String[] selectedRoles, @RequestParam(value = "firstName", required = false) String firstName,
                         @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "password", required = false) String password) {
        User user = new User(firstName, lastName, email, password);
        if (selectedRoles != null) {
            user.setRoles(roleService.findRolesByNames(selectedRoles));
        } else {
            user.setRoles(roleService.findRoleByName("ROLE_USER"));
        }
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
