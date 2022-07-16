package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String viewHomePage(Model model, Principal principal) {
        List<User> users = userService.listAll();
        User admin = userService.getUserByEmail(principal.getName());
        model.addAttribute("users", users);
        model.addAttribute("email", admin.getEmail());
        model.addAttribute("role", admin.getAllRolesString());
        model.addAttribute("roles",roleService.getAllRoles());

        return "admin";
    }

    @GetMapping("/new")
    public String showNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "new_user";
    }

//    Новый метод для поиска по роли
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam(value = "selectRoles[]") String[] arr) {
        List<Role> setOfRoles = new ArrayList<>();

        for (String s : arr) {
            setOfRoles.add(roleService.getRoleById(Long.valueOf(s)));
        }

        user.setRoles(setOfRoles);
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);

        return "redirect:/admin";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditUserPage(@PathVariable(name = "id")Long id) {
        ModelAndView mav = new ModelAndView("edit_user");
        User user = userService.get(id);
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id")Long id){
        userService.delete(id);
        return "redirect:/admin";
    }

}