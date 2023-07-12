package br.com.test.simplesdental.exceptions;

import br.com.test.simplesdental.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, ErrorResponse errorResponse, HttpStatus httpStatus) {
        logException(ex);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleBusinessException(Exception ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .codigo("99")
                .descricao("Ocorreu um erro inesperado. Entre em contato com o suporte técnico")
                .build();
        return handleExceptionInternal(ex, errorResponse , HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<Object> handleBusinessException(ApiException ex, WebRequest request) {
        logException(ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .codigo(ex.getError().getCode())
                .descricao(ex.getError().getDescricao())
                .build();
        return handleExceptionInternal(ex, errorResponse, ex.getError().getHttpStatusCode());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleBusinessException(ConstraintViolationException ex, WebRequest request) {
        logException(ex);
        log.info("ConstraintViolations: {}", ex.getConstraintViolations().toString());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .codigo("2")
                .descricao("Parâmetros obrigatórios não informados")
                .build();
        return handleExceptionInternal(ex, errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .codigo("3")
                .descricao("JSON parse error")
                .build();
        return handleExceptionInternal(ex, errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException ex, WebRequest request){
        BindingResult bindingResult = ex.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors().stream()
                .map(f -> f.getField().concat(": ").concat(Optional.ofNullable(f.getDefaultMessage()).orElse("")))
                .collect(Collectors.joining(" | "));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .codigo("3")
                .descricao(errorMsg)
                .build();

        return handleExceptionInternal(ex, errorResponse, HttpStatus.BAD_REQUEST);
    }


    private static void logException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        ex.printStackTrace();
    }
}
