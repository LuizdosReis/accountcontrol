package br.com.accountcontrol.category.dto;

import br.com.accountcontrol.category.model.Type;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryCreateDTO {

    @NotEmpty(message = "The description not be empty")
    private String description;

    @NotNull(message = "The Type not be empty")
    private Type type;
}
