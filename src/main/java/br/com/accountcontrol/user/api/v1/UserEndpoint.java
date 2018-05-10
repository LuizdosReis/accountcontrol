package br.com.accountcontrol.user.api.v1;

import br.com.accountcontrol.user.User;
import br.com.accountcontrol.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UserEndpoint.BASE_URL)
@AllArgsConstructor
public class UserEndpoint {

    public static final String BASE_URL = "/v1/user";

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Valid User user) {
        return userService.save(user);
    }
}
