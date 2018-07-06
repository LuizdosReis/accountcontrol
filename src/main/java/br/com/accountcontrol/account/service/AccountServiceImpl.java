package br.com.accountcontrol.account.service;

import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.dto.AccountUpdateDto;
import br.com.accountcontrol.account.mapper.AccountMapper;
import br.com.accountcontrol.account.model.Account;
import br.com.accountcontrol.account.repository.AccountRepository;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import br.com.accountcontrol.user.service.UserService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final UserService userService;
    private final AccountMapper accountMapper;
    private static final String ACCOUNT_NOT_FOUND = "account not found";

    @Override
    public AccountReturnDTO save(AccountCreateDTO dto) {
        log.debug("saving account");

        Account account = accountMapper.accountCreateDTOToAccount(dto);
        account.setUser(userService.getCurrentUser());

        return accountMapper.accountToAccountReturnDTO(repository.save(account));
    }

    @Override
    public Page<AccountReturnDTO> findAll(Pageable pageable) {
        log.debug("get all accounts");

        Page<Account> accountsPage = repository.findAllByUser(userService.getCurrentUser(), pageable);

        List<AccountReturnDTO> accountsReturns = Lists.newArrayList();

        accountsPage.stream().forEach(a -> accountsReturns.add(accountMapper.accountToAccountReturnDTO(a)));

        return new PageImpl<>(accountsReturns, pageable, accountsPage.getTotalElements());

    }

    @Override
    public AccountReturnDTO update(AccountUpdateDto dto) {
        log.debug("update category");

        Account account = repository.findByIdAndUser(dto.getId(), userService.getCurrentUser())
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND));
        account.setDescription(dto.getDescription());

        return accountMapper.accountToAccountReturnDTO(repository.save(account));
    }


}
