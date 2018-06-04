package br.com.accountcontrol.category.model;

import br.com.accountcontrol.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @ManyToOne
    private User user;

}
