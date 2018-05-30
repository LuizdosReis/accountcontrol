package br.com.accountcontrol.category.service;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryReturnDTO save(CategoryCreateDTO category);

    Page<CategoryReturnDTO> findAll(Pageable pageable);

    CategoryReturnDTO update(CategoryUpdateDTO category);

    CategoryReturnDTO findById(Long id);

}
