package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    private RecipesUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/api/register")
    public void register(@RequestBody @Valid UserEntity user) {
        if (user.getPassword().length() >= 8) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            userDetailsService.saveUser(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
