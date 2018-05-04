package br.com.accountcontrol.category;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public Category save(CategoryCreateDTO category) {
        log.debug("saving category");
        return repository.save(modelMapper.map(category, Category.class));
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        log.debug("get all categories");
        return repository.findAll(pageable);
    }

    @Override
    public Category update(CategoryUpdateDTO category) {
        log.debug("update category");
        return repository.save(modelMapper.map(category, Category.class));
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

}
