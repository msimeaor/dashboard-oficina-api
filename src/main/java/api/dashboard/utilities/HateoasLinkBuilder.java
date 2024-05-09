package api.dashboard.utilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.IntStream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class HateoasLinkBuilder {

  private Class<?> controller;
  private String nomeMetodo;
  private Object[] valoresParametros;

  private void setarAtributos(Class<?> controller, String nomeMetodo, Object[] valoresParametros) {
    this.controller = controller;
    this.nomeMetodo = nomeMetodo;
    this.valoresParametros = valoresParametros;
  }

  public Link gerarLinkFiltrando(Class<?> controller, String nomeMetodo, Object[] valoresParametros, String rel) {
    setarAtributos(controller, nomeMetodo, valoresParametros);
    Method metodo = recuperarMetodo();

    return linkTo(metodo, valoresParametros).withRel(rel);
  }

  private Method recuperarMetodo() {
    Method metodo = null;
    Class<?>[] tiposParametros = recuperarTiposDosParametros();

    try {
      metodo = this.controller.getMethod(this.nomeMetodo, tiposParametros);
    } catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }

    return metodo;
  }

  private Class<?>[] recuperarTiposDosParametros() {
    Class<?>[] tiposParametros = new Class<?>[this.valoresParametros.length];
    IntStream.range(0, this.valoresParametros.length).forEach(index -> {
      Object valorParametro = this.valoresParametros[index];
      tiposParametros[index] = valorParametro.getClass();
    });

    return tiposParametros;
  }

}
