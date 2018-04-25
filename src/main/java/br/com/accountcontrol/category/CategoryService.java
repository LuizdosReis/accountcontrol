package br.com.accountcontrol.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category save(Category category);

    Page<Category> findAll(Pageable pageable);
}
