package com.jeffersonsantos.agendamento_de_veiculos.services;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Cliente;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Endereco;
import com.jeffersonsantos.agendamento_de_veiculos.models.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ViacepService viacepService;

    @Autowired
    public ClientService(ClientRepository clientRepository, ViacepService viacepService) {
        this.clientRepository = clientRepository;
        this.viacepService = viacepService;
    }

    // Insere um novo cliente na tabela.
    public Cliente insertClient(Cliente cliente) {
        return this.clientRepository.save(cliente);
    }


    // Exibe todos os clientes da tabela.

    public List<Cliente> getAllClient() {
        return this.clientRepository.findAll();
    }

    // Exibe o cliente através do id.

    public Optional<Cliente> getClientById(Long id) {
        return this.clientRepository.findById(id);
    }

    // Busca o endereço do cliente
    public Endereco fetchEndereco(String cep) {
        try {
            return viacepService.getEndereco(cep);
        } catch(IOException e) {
            return null;
        }
    }

    // Atualiza um cliente existente.
    public Cliente updateClient(Cliente updatedClient) {
        return this.clientRepository.save(updatedClient);
    }

    // Remove um cliente pelo ID.
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
