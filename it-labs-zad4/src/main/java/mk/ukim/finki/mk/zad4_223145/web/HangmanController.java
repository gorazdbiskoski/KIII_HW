package mk.ukim.finki.mk.zad4_223145.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.mk.zad4_223145.service.HangmanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class HangmanController {
    private final HangmanService hangmanService;

    public HangmanController(HangmanService hangmanService) {
        this.hangmanService = hangmanService;
    }

    @GetMapping("/hangman")
    public String date(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> userCookie = Arrays.stream(cookies)
                    .filter(cookie -> "username".equals(cookie.getName()))
                    .findFirst();
            Optional<Cookie> roleCookie = Arrays.stream(cookies)
                    .filter(cookie -> "role".equals(cookie.getName()))
                    .findFirst();

            if (roleCookie.isPresent()) {
                if(userCookie.isPresent())
                {
                    model.addAttribute("username", userCookie.get().getValue());
                }
                if(roleCookie.isPresent())
                {
                    model.addAttribute("role", roleCookie.get().getValue());
                }
                return "hangman";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/hangman")
    public String getHangman(@RequestParam String choice, @RequestParam String inputField, Model model, HttpServletRequest request) throws IOException, InterruptedException {
        String hangmanResult = hangmanService.getHangmanResult(choice, inputField);
        model.addAttribute("hangmanResult", hangmanResult);

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            Optional<Cookie> userCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> "username".equals(cookie.getName()))
                    .findFirst();
            Optional<Cookie> roleCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> "role".equals(cookie.getName()))
                    .findFirst();

            if (roleCookie.isPresent()) {
                if(userCookie.isPresent())
                {
                    model.addAttribute("username", userCookie.get().getValue());
                }
                if(roleCookie.isPresent())
                {
                    model.addAttribute("role", roleCookie.get().getValue());
                }
            }
        }

        return "hangman";
    }
}
