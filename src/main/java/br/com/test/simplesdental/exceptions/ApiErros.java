package br.com.test.simplesdental.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ApiErros {
    API9999("Ocorreu um erro não mapeado", HttpStatus.INTERNAL_SERVER_ERROR),
    API0001("Profissional não encontrado com o Id informado", HttpStatus.NOT_FOUND);

    @Getter
    private String descricao;

    @Getter
    private HttpStatus httpStatusCode;


    ApiErros(String description, HttpStatus httpStatusCode) {
        this.descricao = description;
        this.httpStatusCode = httpStatusCode;
    }

    ApiErros(String description) {
        this.descricao = description;
    }

    public String getCode() {
        return this.name();
    }
}
