package br.com.accountcontrol.category.dto;

import br.com.accountcontrol.category.model.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReturnDTO {

    private Long id;
    private String description;
    private Type type;


}
