package br.com.accountcontrol.category.dto;

import br.com.accountcontrol.category.model.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryCreateDTO {

    @NotBlank(message = "The description not be empty")
    private String description;

    @NotNull(message = "The Type not be empty")
    private Type type;
}
