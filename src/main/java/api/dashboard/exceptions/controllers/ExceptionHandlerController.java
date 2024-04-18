package api.dashboard.exceptions.controllers;

import api.dashboard.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

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
