package br.com.accountcontrol.account.api.v1;

import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(AccountEndpoint.BASE_URL)
@AllArgsConstructor
public class AccountEndpoint {

    public static final String BASE_URL = "/v1/accounts";

    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountReturnDTO save(@Valid @RequestBody AccountCreateDTO account) {
        return service.save(account);
    }
}
