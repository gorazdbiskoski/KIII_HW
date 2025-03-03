package mk.ukim.finki.mk.zad4_223145.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.mk.zad4_223145.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping({"/", "/application"})
    public String showLoginForm(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            Optional<Cookie> roleCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> "role".equals(cookie.getName()))
                    .findFirst();

            Optional<Cookie> userCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> "username".equals(cookie.getName()))
                    .findFirst();
            if (roleCookie.isPresent()) {
                String role = roleCookie.get().getValue();
                String username = userCookie.get().getValue();
                model.addAttribute("role", role);
                model.addAttribute("username", username);
                if ("DATE".equals(role)) {
                    return "redirect:/date";
                } else if ("HANGMAN".equals(role)) {
                    return "redirect:/hangman";
                }
            }
        }
        return "login";
    }


    @PostMapping({"/", "/application"})
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        String role = loginService.validateLogin(username, password);
        if (role != null) {
            Cookie userCookie = new Cookie("username", username);
            Cookie roleCookie = new Cookie("role", role);
            userCookie.setPath("/");
            roleCookie.setPath("/");
            response.addCookie(userCookie);
            response.addCookie(roleCookie);

            model.addAttribute("role", role);
            model.addAttribute("username", username);

            if (role.equals("DATE")) {
                return "redirect:/date";
            } else if (role.equals("HANGMAN")) {
                return "redirect:/hangman";
            }
        }
        model.addAttribute("loginError", true);
        return "login";
    }
}
