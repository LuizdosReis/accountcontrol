package br.com.accountcontrol.erro;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorDetails implements AbstractError {

    private String title;
    private int status;
    private String detail;
    private LocalDateTime date;
    private String developerMessage;
}
