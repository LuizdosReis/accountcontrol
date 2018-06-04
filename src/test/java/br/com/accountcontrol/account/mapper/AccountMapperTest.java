package br.com.accountcontrol.account.mapper;

import br.com.accountcontrol.account.builder.AccountBuilder;
import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.model.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountMapperTest {

    AccountMapper accountMapper = AccountMapper.INSTANCE;

    @Test
    public void shouldMapCategoryCreateDtoToCategory() {
        AccountCreateDTO accountCreateDto = AccountBuilder.ACCOUNT_CREATE_DTO;

        Account account = accountMapper.accountCreateDTOToAccount(accountCreateDto);

        assertEquals(accountCreateDto.getDescription(), account.getDescription());
        assertEquals(accountCreateDto.getBalance(), account.getBalance());
    }

    @Test
    public void shouldMapCategoryToCategoryReturnDto() {
        Account account = AccountBuilder.ACCOUNT;

        AccountReturnDTO accountReturnDTO = accountMapper.accountToAccountReturnDTO(account);

        assertEquals(account.getDescription(), accountReturnDTO.getDescription());
        assertEquals(account.getBalance(), accountReturnDTO.getBalance());
        assertEquals(account.getId(), accountReturnDTO.getId());

    }
}
