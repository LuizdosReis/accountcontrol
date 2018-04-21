package br.com.accountcontrol.erro;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldError {
    private String field;
    private String message;
    private String code;
    private Object rejectedValue;
}
