package br.com.accountcontrol.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountReturnDTO {

    private Long id;
    private String description;
    private BigDecimal balance;

}
