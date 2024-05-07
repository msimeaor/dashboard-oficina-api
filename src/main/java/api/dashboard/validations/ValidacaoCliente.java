package api.dashboard.validations;

import api.dashboard.exceptions.ConflictException;
import api.dashboard.model.dtos.request.ClienteRequestDTO;
import api.dashboard.model.repositories.ClienteRepository;
import api.dashboard.model.repositories.TelefoneRepository;
import api.dashboard.validations.interfaces.Validacao;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoCliente implements Validacao<ClienteRequestDTO> {

  private ClienteRepository clienteRepository;
  private TelefoneRepository telefoneRepository;

  public ValidacaoCliente(ClienteRepository clienteRepository,
                          TelefoneRepository telefoneRepository) {

    this.clienteRepository = clienteRepository;
    this.telefoneRepository = telefoneRepository;
  }

  @Override
  public void validar(ClienteRequestDTO objeto) {
    validarCPF(objeto);
    validarTelefone(objeto);
    validarEmail(objeto);
  }

  private void validarCPF(ClienteRequestDTO clienteRequestDTO) {
    if (clienteRepository.findByCpf(clienteRequestDTO.getCpf()).isPresent())
      throw new ConflictException("Verificamos que já existem clientes cadastrados com este CPF!");
  }

  private void validarTelefone(ClienteRequestDTO clienteRequestDTO) {
    String numeroTelefoneCliente = clienteRequestDTO.getTelefoneRequestDTO().getNumero();
    if (telefoneRepository.findByNumero(numeroTelefoneCliente).isPresent())
      throw new ConflictException("Verificamos que já existem clientes cadastrados com este número de telefone!");
  }

  private void validarEmail(ClienteRequestDTO clienteRequestDTO) {
    if (clienteRepository.findByEmail(clienteRequestDTO.getEmail()).isPresent())
      throw new ConflictException("Verificamos que já existem clientes cadastrados com este email!");
  }

}
