package br.com.accountcontrol.category.mapper;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category categoryCreateDTOToCategory(CategoryCreateDTO categoryCreateDTO);

    CategoryReturnDTO CategoryToCategoryReturnDTO(Category category);
}
