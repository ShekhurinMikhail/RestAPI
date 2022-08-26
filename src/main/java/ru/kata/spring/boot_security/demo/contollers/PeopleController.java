package ru.kata.spring.boot_security.demo.contollers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin/people")
public class PeopleController {
    private final UserService userService;

    public PeopleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("people", userService.getAllUsers());
        return "people/people";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "people/new";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/people";
    }

    @GetMapping(params = {"id"})
    public String showUser(@RequestParam(value = "id", required = false) long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "people/showUser";
    }
    @DeleteMapping(params = "id")
    public String delete(@RequestParam(value = "id", required = false) long id){
        userService.deleteUser(id);
        return "redirect:/admin/people";
    }

    @GetMapping(value = "/edit", params = "id")
    public String edit(Model model, @RequestParam(value = "id", required = false) long id) {
        model.addAttribute("user", userService.showUser(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/people";
    }
}
