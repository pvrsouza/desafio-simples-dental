package br.com.test.simplesdental.exceptions;

public class NotFoundException extends ApiException {
    public NotFoundException(ApiErros error) {
        super(error);
    }
}
