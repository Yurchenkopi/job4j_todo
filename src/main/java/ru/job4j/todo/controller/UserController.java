package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.controller.utils.TimeZoneLoader;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final TimeZoneLoader timeZoneLoader;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "timezone", new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                String timezoneValue = timeZoneLoader.getTimeZoneIds().get(Integer.parseInt(text));
                setValue(timezoneValue);
            }
        });
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        var zones = timeZoneLoader.getTimeZoneIds().entrySet();
        model.addAttribute("timeZoneIds", zones);
        return "users/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @GetMapping("/info")
    public String getUserInfo(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "users/info";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var newUser = userService.save(user);
        if (newUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким login уже зарегистрирован");
            return "/users/register";
        }
        return loginUser(user, model, request);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Логин или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/tasks";
    }
}
