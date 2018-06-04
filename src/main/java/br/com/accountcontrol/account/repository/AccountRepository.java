package br.com.accountcontrol.account.repository;

import br.com.accountcontrol.account.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
}
