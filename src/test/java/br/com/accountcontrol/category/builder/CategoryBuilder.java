package br.com.accountcontrol.category.builder;

import br.com.accountcontrol.category.Category;
import br.com.accountcontrol.category.Type;

public class CategoryBuilder {

    public static Category buildCategoryWithId(){
        return Category.builder()
                .description("Car")
                .type(Type.OUT)
                .id(1L)
                .build();
    }

    public static Category buildCategoryWithoutId(){
        return Category.builder()
                .description("Car")
                .type(Type.OUT)
                .build();
    }
}
