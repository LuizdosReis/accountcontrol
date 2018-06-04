package br.com.accountcontrol.account.service;

import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.mapper.AccountMapper;
import br.com.accountcontrol.account.model.Account;
import br.com.accountcontrol.account.repository.AccountRepository;
import br.com.accountcontrol.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final UserService userService;
    private final AccountMapper accountMapper;

    @Override
    public AccountReturnDTO save(AccountCreateDTO dto) {
        log.debug("saving account");

        Account account = accountMapper.accountCreateDTOToAccount(dto);
        account.setUser(userService.getCurrentUser());

        return accountMapper.accountToAccountReturnDTO(repository.save(account));
    }
}
