package br.com.test.simplesdental.exceptions;

import lombok.Getter;

public class ApiException extends RuntimeException{

    @Getter
    private ApiErros error;

    public ApiException(ApiErros error){
        super(error.getDescricao());
        this.error = error;
    }
}
