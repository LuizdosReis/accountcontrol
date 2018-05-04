package br.com.accountcontrol.category;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category save(CategoryCreateDTO category);

    Page<Category> findAll(Pageable pageable);

    Category update(CategoryUpdateDTO category);

    Category findById(Long id);
    
}
