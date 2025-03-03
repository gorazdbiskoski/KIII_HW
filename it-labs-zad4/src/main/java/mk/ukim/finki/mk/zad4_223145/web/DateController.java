package mk.ukim.finki.mk.zad4_223145.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.mk.zad4_223145.service.DateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class DateController {
    private final DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping("/date")
    public String date(Model model,
                       HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            Optional<Cookie> cookie = Arrays.stream(cookies)
                    .filter(c -> "username".equals(c.getName())).findFirst();
            Optional<Cookie> cookieRole = Arrays.stream(cookies)
                    .filter(c -> "role".equals(c.getName())).findFirst();

            if(cookieRole.isPresent())
            {
                if(cookie.isPresent())
                {
                    model.addAttribute("user", cookie.get().getValue());
                }
                if(cookieRole.isPresent()) {
                    model.addAttribute("role", cookieRole.get().getValue() );
                }
                return "date";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/date")
    public String getDate(@RequestParam String choice,
                          @RequestParam String inputField,
                          Model model,
                          HttpServletRequest request) throws IOException, InterruptedException {
        String dateResult = dateService.getDate(choice, inputField);
        model.addAttribute("dateResult", dateResult);
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                    .filter(c -> "username".equals(c.getName()))
                    .findFirst();
            Optional<Cookie> cookieRole = Arrays.stream(request.getCookies())
                    .filter(c -> "role".equals(c.getName()))
                    .findFirst();

            if(cookie.isPresent())
            {
                model.addAttribute("user", cookie.get().getValue());
            }
            if(cookieRole.isPresent()) {
                model.addAttribute("role", cookieRole.get().getValue());
            }
        }

        return "date";
    }
}
