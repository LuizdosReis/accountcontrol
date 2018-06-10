package br.com.accountcontrol.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountCreateDTO {

    @NotBlank(message = "The description not be empty")
    private String description;

    @NotNull(message = "The balance not be empty")
    @Digits(fraction = 2, integer = 9)
    private BigDecimal balance;
}
