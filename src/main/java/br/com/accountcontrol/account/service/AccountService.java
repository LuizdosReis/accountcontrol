package br.com.accountcontrol.account.service;

import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;

public interface AccountService {

    AccountReturnDTO save(AccountCreateDTO dto);

}
