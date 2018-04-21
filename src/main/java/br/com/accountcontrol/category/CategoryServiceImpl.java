package br.com.accountcontrol.category;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

}
