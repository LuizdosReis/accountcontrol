package br.com.accountcontrol.category.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryUpdateDTO {

    @NotNull(message = "the id not be empty")
    @Min(message = "the id should be positive", value = 1)
    private Long id;

    @NotEmpty(message = "The description not be empty")
    private String description;
}
