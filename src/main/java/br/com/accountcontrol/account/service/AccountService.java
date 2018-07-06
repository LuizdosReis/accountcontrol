package br.com.accountcontrol.account.service;

import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.dto.AccountUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    AccountReturnDTO save(AccountCreateDTO dto);

    Page<AccountReturnDTO> findAll(Pageable pageable);
    
    AccountReturnDTO update(AccountUpdateDto dto);
}
