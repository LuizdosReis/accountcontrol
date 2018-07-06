package br.com.accountcontrol.account.builder;

import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.dto.AccountUpdateDto;
import br.com.accountcontrol.account.model.Account;

import java.math.BigDecimal;

public class AccountBuilder {

    public static final AccountCreateDTO ACCOUNT_CREATE_DTO = AccountCreateDTO
            .builder()
            .description("description")
            .balance(new BigDecimal("20"))
            .build();

    public static final Account ACCOUNT = Account
            .builder()
            .id(1L)
            .description("description")
            .balance(new BigDecimal("20"))
            .build();

    public static final AccountReturnDTO ACCOUNT_RETURN_DTO = AccountReturnDTO
            .builder()
            .description("description")
            .balance(new BigDecimal("20"))
            .build();

    public static final AccountUpdateDto ACCOUNT_UPDATE_DTO = AccountUpdateDto
            .builder()
            .description("update description")
            .id(1L)
            .build();
}
