package api.dashboard.model.services.impl;

import api.dashboard.model.dtos.request.ClienteRequestDTO;
import api.dashboard.model.dtos.response.ClienteResponseDTO;
import api.dashboard.model.dtos.response.EstatisticasDTO;
import api.dashboard.model.entities.Cliente;
import api.dashboard.model.entities.Telefone;
import api.dashboard.model.services.ClienteService;
import api.dashboard.utilities.Calculos;
import api.dashboard.utilities.businessLogicEndpoints.clientes.LogicaCadastrarCliente;
import api.dashboard.utilities.searches.AcessoDadosClientes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  private AcessoDadosClientes acessoDadosClientes;
  private Calculos calculos;
  private LogicaCadastrarCliente logicaCadastrarCliente;

  public ClienteServiceImpl(AcessoDadosClientes acessoDadosClientes,
                            Calculos calculos,
                            LogicaCadastrarCliente logicaCadastrarCliente) {

    this.acessoDadosClientes = acessoDadosClientes;
    this.calculos = calculos;
    this.logicaCadastrarCliente = logicaCadastrarCliente;
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasClientes() {
    Integer totalClientes = acessoDadosClientes.getTotalRegistrosCadastrados();
    Double porcentagemCrescimentoClientesUltimoMes = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAoTotal(acessoDadosClientes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Clientes", totalClientes, porcentagemCrescimentoClientesUltimoMes);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<EstatisticasDTO> getEstatisticasClientesByMes(Integer valorMes) {
    Integer totalClientes = acessoDadosClientes.getRegistrosCadastradosMesEspecifico(valorMes);
    Double porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado = calculos
            .calcularCrescimentoUltimoMesEmRelacaoAMesSelecionado(acessoDadosClientes, valorMes);
    EstatisticasDTO estatisticasDTO = EstatisticasDTO.newEstatisticasDTO(
            "Clientes", totalClientes, porcentagemCrescimentoUltimoMesEmRelacaoAoMesSelecionado);

    return new ResponseEntity<>(estatisticasDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ClienteResponseDTO> cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
    logicaCadastrarCliente.validarClienteRequestDTO(clienteRequestDTO);
    Telefone telefone = logicaCadastrarCliente.criarESalvarTelefoneDoCliente(clienteRequestDTO.getTelefoneRequestDTO());
    Cliente cliente = logicaCadastrarCliente.criarESalvarCliente(clienteRequestDTO, telefone);
    logicaCadastrarCliente.vincularTelefoneAoCliente(telefone, cliente);
    ClienteResponseDTO clienteResponseDTO = logicaCadastrarCliente.criarLinksHateoasDeCliente(cliente);

    return new ResponseEntity<>(clienteResponseDTO, HttpStatus.CREATED);
  }

}
