package br.com.accountcontrol.category.builder;

import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;
import br.com.accountcontrol.category.model.Category;
import br.com.accountcontrol.category.model.Type;

public class CategoryBuilder {

    public static Category CATEGORY = Category.builder()
            .description("Car")
            .type(Type.OUT)
            .id(1L)
            .build();

    public static CategoryReturnDTO CATEGORY_RETURN_DTO = CategoryReturnDTO.builder()
            .description("Car")
            .type(Type.OUT)
            .id(1L)
            .build();

    public static CategoryCreateDTO CATEGORY_CREATE_DTO = CategoryCreateDTO.builder()
            .description("Car")
            .type(Type.OUT)
            .build();

    public static CategoryUpdateDTO CATEGORY_UPDATE_DTO = CategoryUpdateDTO.builder()
            .id(1L)
            .description("Car")
            .build();
}
