package br.com.accountcontrol.category.mapper;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.model.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {


    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void shouldMapCategoryCreateDtoToCategory() {
        CategoryCreateDTO categoryCreateDto = CategoryBuilder.CATEGORY_CREATE_DTO;

        Category category = categoryMapper.categoryCreateDTOToCategory(categoryCreateDto);

        assertEquals(categoryCreateDto.getDescription(), category.getDescription());
    }

    @Test
    public void shouldMapCategoryToCategoryReturnDto() {
        Category category = CategoryBuilder.CATEGORY;

        CategoryReturnDTO categoryReturnDTO = categoryMapper.categoryToCategoryReturnDTO(category);

        assertEquals(category.getId(), categoryReturnDTO.getId());
        assertEquals(category.getDescription(), categoryReturnDTO.getDescription());
        assertEquals(category.getType(), categoryReturnDTO.getType());
    }
}
