package br.com.accountcontrol.category.builder;

import br.com.accountcontrol.category.Category;
import br.com.accountcontrol.category.Type;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryUpdateDTO;

public class CategoryBuilder {

    public static Category CATEGORY = Category.builder()
            .description("Car")
            .type(Type.OUT)
            .id(1L)
            .build();

    public static Category CATEGORY_WITHOUT_ID = Category.builder()
            .description("Car")
            .type(Type.OUT)
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
