package br.com.accountcontrol.account.service;

import br.com.accountcontrol.account.builder.AccountBuilder;
import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.mapper.AccountMapper;
import br.com.accountcontrol.account.model.Account;
import br.com.accountcontrol.account.repository.AccountRepository;
import br.com.accountcontrol.user.service.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private AccountRepository repository;

    @Mock
    private UserService userService;

    private AccountService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new AccountServiceImpl(repository, userService, AccountMapper.INSTANCE);
    }

    @Test
    public void save() {
        Account accountReturned = AccountBuilder.ACCOUNT;
        AccountCreateDTO accountCreateDto = AccountBuilder.ACCOUNT_CREATE_DTO;

        when(repository.save(Mockito.any(Account.class))).thenReturn(accountReturned);

        AccountReturnDTO account = service.save(accountCreateDto);

        assertEquals(accountReturned.getId(), account.getId());
        assertEquals(accountReturned.getDescription(), account.getDescription());
        assertEquals(accountReturned.getBalance(), account.getBalance());
    }
}
