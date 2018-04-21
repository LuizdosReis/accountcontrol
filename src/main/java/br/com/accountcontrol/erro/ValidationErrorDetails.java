package br.com.accountcontrol.erro;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
public class ValidationErrorDetails implements AbstractError{

    private String title;
    private int status;
    private String detail;
    private LocalDateTime date;
    private String developerMessage;
    private List<FieldError> fieldErrors;
    private List<GlobalError> globalErrors;
}
