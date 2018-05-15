package br.com.accountcontrol.category;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import br.com.accountcontrol.user.service.UserService;
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

    public static final String CATEGORY_NOT_FOUND = "Category not found";
    private final CategoryRepository repository;
    private final UserService userService;

    private final ModelMapper modelMapper;

    @Override
    public Category save(CategoryCreateDTO dto) {
        log.debug("saving category");

        Category category = modelMapper.map(dto, Category.class);
        category.setUser(userService.getCurrentUser());

        return repository.save(category);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        log.debug("get all categories");
        return repository.findAllByUser(userService.getCurrentUser(), pageable);
    }

    @Override
    public Category update(CategoryUpdateDTO dto) {
        log.debug("update category");

        Category category = repository.findByIdAndUser(dto.getId(), userService.getCurrentUser())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        category.setDescription(dto.getDescription());

        return repository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return repository.findByIdAndUser(id, userService.getCurrentUser())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));
    }

}
