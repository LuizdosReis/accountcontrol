package br.com.accountcontrol.category.service;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.category.mapper.CategoryMapper;
import br.com.accountcontrol.category.model.Category;
import br.com.accountcontrol.category.repository.CategoryRepository;
import br.com.accountcontrol.exception.ResourceNotFoundException;
import br.com.accountcontrol.user.service.UserService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_NOT_FOUND = "Category not found";
    private final CategoryRepository repository;
    private final UserService userService;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryReturnDTO save(CategoryCreateDTO dto) {
        log.debug("saving category");

        Category category = categoryMapper.categoryCreateDTOToCategory(dto);
        category.setUser(userService.getCurrentUser());

        return categoryMapper.categoryToCategoryReturnDTO(repository.save(category));
    }

    @Override
    public Page<CategoryReturnDTO> findAll(Pageable pageable) {
        log.debug("get all categories");

        Page<Category> categoriesPage = repository.findAllByUser(userService.getCurrentUser(), pageable);

        List<CategoryReturnDTO> categoriesReturns = Lists.newArrayList();

        categoriesPage.stream().forEach(c -> categoriesReturns.add(categoryMapper.categoryToCategoryReturnDTO(c)));

        return new PageImpl<>(categoriesReturns, pageable, categoriesPage.getTotalElements());
    }

    @Override
    public CategoryReturnDTO update(CategoryUpdateDTO dto) {
        log.debug("update category");

        Category category = repository.findByIdAndUser(dto.getId(), userService.getCurrentUser())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));
        category.setDescription(dto.getDescription());

        return categoryMapper.categoryToCategoryReturnDTO(repository.save(category));
    }

    @Override
    public CategoryReturnDTO findById(Long id) {
        Category category = repository.findByIdAndUser(id, userService.getCurrentUser())
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND));

        return categoryMapper.categoryToCategoryReturnDTO(category);
    }

}
