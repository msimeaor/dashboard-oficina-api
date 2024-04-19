package api.dashboard.exceptions.controllers;

import api.dashboard.exceptions.ExceptionResponse;
import api.dashboard.exceptions.ZeroCountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ZeroCountException.class)
  public ResponseEntity<ExceptionResponse> zeroCountException(Exception ex, WebRequest request) {
    return criarERetornarExceptionResponse(ex, request, HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<ExceptionResponse> criarERetornarExceptionResponse(Exception ex,
                                                                           WebRequest request,
                                                                           HttpStatus status) {

    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
            .codigoHttpErro(status.value())
            .mensagemErro(ex.getMessage())
            .detalhesErro(request.getDescription(false))
            .timestamp(new Date())
            .build();

    return new ResponseEntity<>(exceptionResponse, status);
  }

}
