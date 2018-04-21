package br.com.accountcontrol.category.api.v1;

import br.com.accountcontrol.category.Category;
import br.com.accountcontrol.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(CategoryEndpoint.BASE_URL)
@AllArgsConstructor
public class CategoryEndpoint {

    public static final String BASE_URL = "/v1/categories";

    private final CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category save(@Valid @RequestBody Category category){
        return service.save(category);
    }
}
