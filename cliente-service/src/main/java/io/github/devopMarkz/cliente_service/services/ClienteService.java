package io.github.devopMarkz.cliente_service.services;

import io.github.devopMarkz.cliente_service.exceptions.ClienteInexistenteException;
import io.github.devopMarkz.cliente_service.model.Cliente;
import io.github.devopMarkz.cliente_service.repositories.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        if(clienteRepository.existsByCpf(cliente.getCpf())){
            throw new ClienteInexistenteException("Cliente inexistente!");
        }

        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> findByFilters(Long id, String nome, String cpf, boolean isFidelizado) {
        Cliente cliente = new Cliente(id, nome, cpf, isFidelizado);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Cliente> example = Example.of(cliente, matcher);

        return this.clienteRepository.findAll(example);
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).
                orElseThrow(() -> new ClienteInexistenteException("Cliente inexistente!"));
    }

    @Transactional(readOnly = true)
    public Cliente findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteInexistenteException("Cliente inexistente!"));
    }

    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteInexistenteException("Cliente inexistente!"));

        clienteExistente.setNome(cliente.getNome() == null? clienteExistente.getNome() : cliente.getNome());
    }

    @Transactional
    public void efetuarCompra(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteInexistenteException("Cliente inexistente!"));

        cliente.efetuarCompra();
    }

    @Transactional
    public void cancelarCompra(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteInexistenteException("Cliente inexistente!"));

        cliente.cancelarCompra();
    }

}
