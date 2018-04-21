package br.com.accountcontrol.category;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Data
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotEmpty(message = "The description not be empty")
    private String description;

    @NotNull(message = "The Type not be empty")
    @Enumerated(value = EnumType.STRING)
    private Type type;

}
