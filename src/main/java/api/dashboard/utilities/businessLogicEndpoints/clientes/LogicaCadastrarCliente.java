package api.dashboard.utilities.businessLogicEndpoints.clientes;

import api.dashboard.controllers.TelefoneRestController;
import api.dashboard.dozermapper.DozzerMapper;
import api.dashboard.model.dtos.request.ClienteRequestDTO;
import api.dashboard.model.dtos.request.TelefoneRequestDTO;
import api.dashboard.model.dtos.response.ClienteResponseDTO;
import api.dashboard.model.entities.Cliente;
import api.dashboard.model.entities.Telefone;
import api.dashboard.model.repositories.ClienteRepository;
import api.dashboard.model.repositories.TelefoneRepository;
import api.dashboard.validations.ValidacaoCliente;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LogicaCadastrarCliente {

  private ValidacaoCliente validacaoCliente;
  private ClienteRepository clienteRepository;
  private TelefoneRepository telefoneRepository;

  public LogicaCadastrarCliente(ValidacaoCliente validacaoCliente,
                                ClienteRepository clienteRepository,
                                TelefoneRepository telefoneRepository) {

    this.validacaoCliente = validacaoCliente;
    this.clienteRepository = clienteRepository;
    this.telefoneRepository = telefoneRepository;
  }

  public void validarClienteRequestDTO(ClienteRequestDTO clienteRequestDTO) {
    validacaoCliente.validar(clienteRequestDTO);
  }

  public Telefone criarESalvarTelefoneDoCliente(TelefoneRequestDTO telefoneRequestDTO) {
    Telefone telefone = DozzerMapper.parseObject(telefoneRequestDTO, Telefone.class);
    return telefoneRepository.save(telefone);
  }

  public Cliente criarESalvarCliente(ClienteRequestDTO clienteRequestDTO, Telefone telefone) {
    Cliente cliente = DozzerMapper.parseObject(clienteRequestDTO, Cliente.class);
    cliente.setTelefone(telefone);
    cliente.setDataCriacao(LocalDate.now());
    return clienteRepository.save(cliente);
  }

  public void vincularTelefoneAoCliente(Telefone telefone, Cliente cliente) {
    telefone.setCliente(cliente);
  }

  public ClienteResponseDTO criarLinksHateoasDeCliente(Cliente cliente) {
    ClienteResponseDTO clienteResponseDTO = DozzerMapper.parseObject(cliente, ClienteResponseDTO.class);
    clienteResponseDTO.add(linkTo(methodOn(TelefoneRestController.class)
            .getTelefoneById(cliente.getTelefone().getId())).withRel("Telefone"));
    return clienteResponseDTO;
  }

}
