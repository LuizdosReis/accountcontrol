package br.com.accountcontrol.erro;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface AbstractError {

    public String getTitle();

    public int getStatus();

    public String getDetail();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public LocalDateTime getDate();

    public String getDeveloperMessage();
}
