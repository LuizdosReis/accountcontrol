package br.com.accountcontrol.account.repository;

import br.com.accountcontrol.account.model.Account;
import br.com.accountcontrol.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Page<Account> findAllByUser(User user, Pageable pageable);

    Optional<Account> findByIdAndUser(Long id, User currentUser);
}
