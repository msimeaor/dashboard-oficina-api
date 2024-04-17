package api.dashboard.dozermapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozzerMapper {

  private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

  public static <O, D> D parseObject(O origem, Class<D> destino) {
    return mapper.map(origem, destino);
  }

  public static <O, D> List<D> parseListObjects(List<O> listaOrigem, Class<D> destino) {
    List<D> listaDestino = new ArrayList<>();
    listaOrigem.forEach(objetoOrigem -> {
      listaDestino.add(mapper.map(objetoOrigem, destino));
    });

    return listaDestino;
  }

}
