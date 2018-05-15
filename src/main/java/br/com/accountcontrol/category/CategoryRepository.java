package br.com.accountcontrol.category;

import br.com.accountcontrol.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Optional<Category> findByIdAndUser(Long id, User user);

    Page<Category> findAllByUser(User user, Pageable pageable);
}
