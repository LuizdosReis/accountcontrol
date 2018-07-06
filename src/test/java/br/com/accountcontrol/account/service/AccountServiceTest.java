package br.com.accountcontrol.account.service;

import br.com.accountcontrol.account.builder.AccountBuilder;
import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.dto.AccountUpdateDto;
import br.com.accountcontrol.account.mapper.AccountMapper;
import br.com.accountcontrol.account.model.Account;
import br.com.accountcontrol.account.repository.AccountRepository;
import br.com.accountcontrol.user.builder.UserBuilder;
import br.com.accountcontrol.user.model.User;
import br.com.accountcontrol.user.service.UserService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private AccountRepository repository;

    @Mock
    private UserService userService;

    private AccountService service;

    private User user;

    @Before
    public void setUp() {
        user = UserBuilder.USER;

        service = new AccountServiceImpl(repository, userService, AccountMapper.INSTANCE);
    }

    @Test
    public void shouldSaveAccount() {
        Account accountReturned = AccountBuilder.ACCOUNT;
        AccountCreateDTO accountCreateDto = AccountBuilder.ACCOUNT_CREATE_DTO;

        when(repository.save(Mockito.any(Account.class))).thenReturn(accountReturned);

        AccountReturnDTO account = service.save(accountCreateDto);

        assertEquals(accountReturned.getId(), account.getId());
        assertEquals(accountReturned.getDescription(), account.getDescription());
        assertEquals(accountReturned.getBalance(), account.getBalance());
    }

    @Test
    public void getAllShouldReturnPageWithElements() {
        PageRequest pageRequest = PageRequest.of(0, 20);

        when(userService.getCurrentUser()).thenReturn(user);
        when(repository.findAllByUser(user, pageRequest))
                .thenReturn(new PageImpl<>(Lists.newArrayList(new Account(), new Account(), new Account())));

        Page<AccountReturnDTO> accountsPage = service.findAll(pageRequest);
        assertEquals(3, accountsPage.getTotalElements());
        assertEquals(1, accountsPage.getTotalPages());
        assertEquals(3, accountsPage.getContent().size());
    }

    @Test
    public void getAllShouldReturnEmptyPage() {
        PageRequest pageRequest = PageRequest.of(0, 20);

        when(userService.getCurrentUser()).thenReturn(user);
        when(repository.findAllByUser(user, pageRequest))
                .thenReturn(new PageImpl<>(Lists.newArrayList()));

        Page<AccountReturnDTO> accountsPage = service.findAll(pageRequest);
        assertEquals(0, accountsPage.getTotalElements());
        assertEquals(0, accountsPage.getTotalPages());
        assertEquals(0, accountsPage.getContent().size());
    }

    @Test
    public void shouldUpdateAccount() {
        Account account = AccountBuilder.ACCOUNT;
        AccountUpdateDto accountUpdateDto = AccountBuilder.ACCOUNT_UPDATE_DTO;

        when(userService.getCurrentUser()).thenReturn(user);
        when(repository.findByIdAndUser(account.getId(), user))
                .thenReturn(Optional.of(account));
        when(repository.save(account)).thenReturn(account);

        AccountReturnDTO accountReturn = service.update(accountUpdateDto);

        assertEquals(accountReturn.getDescription(), accountUpdateDto.getDescription());
        assertEquals(accountReturn.getId(), accountReturn.getId());
        assertEquals(accountReturn.getBalance(), account.getBalance());
        
        verify(repository, times(1)).save(account);

    }

}
