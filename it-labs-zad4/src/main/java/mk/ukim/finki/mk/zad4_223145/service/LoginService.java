package mk.ukim.finki.mk.zad4_223145.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public String validateLogin(String username, String password) {
        if (username.equals("user1") && password.equals("lozinka1")) {
            return "DATE";
        } else if (username.equals("user2") && password.equals("lozinka2")) {
            return "HANGMAN";
        } else if (username.equals("user3") && password.equals("lozinka3")){
            return "HANGMAN";
        } else if (username.equals("user4") && password.equals("lozinka4")) {
            return "DATE";
        }
        return null;
    }
}
