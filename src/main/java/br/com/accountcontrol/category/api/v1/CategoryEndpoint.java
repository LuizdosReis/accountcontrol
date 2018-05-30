package br.com.accountcontrol.category.api.v1;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public CategoryReturnDTO save(@Valid @RequestBody CategoryCreateDTO category) {
        return service.save(category);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryReturnDTO> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryReturnDTO update(@Valid @RequestBody CategoryUpdateDTO category) {
        return service.update(category);
    }

    @GetMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CategoryReturnDTO findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}
