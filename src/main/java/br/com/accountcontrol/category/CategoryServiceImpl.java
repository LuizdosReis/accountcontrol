package br.com.accountcontrol.category;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository repository;

    @Override
    public Category save(Category category){
        log.debug("saving category");
        return repository.save(category);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        log.debug("get all categories");
        return repository.findAll(pageable);
    }

}
