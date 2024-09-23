package com.jeffersonsantos.agendamento_de_veiculos.util;

import com.jeffersonsantos.agendamento_de_veiculos.builder.AddressBuilder;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Cliente;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Endereco;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Veiculo;
import com.jeffersonsantos.agendamento_de_veiculos.services.ClientService;
import com.jeffersonsantos.agendamento_de_veiculos.services.VehicleService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Essa classe têm alguns métodos que validam os dados do cliente antes de criá-los ou atualizá-los.
@Component
public class ClientValidator {
    private final ClientService clientService;
    private final VehicleService vehicleService;

    public ClientValidator(ClientService clientService, VehicleService vehicleService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
    }

    public List<String> validateVehicles(Cliente client) {
        List<String> errorMessages = new ArrayList<>();

        // verifica se a placa do veículo já existe na lista de algum cliente.
        for (Veiculo newVehicle : client.getVeiculos()) {
            boolean vehicleExists = vehicleService.getAllVehicles().stream()
                    .anyMatch(existingVehicle -> existingVehicle.getPlaca().equals(newVehicle.getPlaca()));

            if (vehicleExists) {
                errorMessages.add("A placa "+newVehicle.getPlaca()+" já existe na base de dados.");
            } else { // se não existir, adiciona.
                newVehicle.setCliente(client);
            }
        }
        return errorMessages;
    }

    public List<String> validateAndUpdate(Cliente oldClient, Cliente newClient) {
        List<String> errorMessages = new ArrayList<>();

        if (newClient.getNome() != null && !newClient.getNome().isEmpty()) {
            oldClient.setNome(newClient.getNome());
        }

        if (newClient.getCpf() != null && !newClient.getCpf().isEmpty()) {
            oldClient.setCpf(newClient.getCpf());
        }

        if (newClient.getEndereco() != null && !newClient.getEndereco().isEmpty()) {
            String cep = newClient.getEndereco();
            Endereco address = clientService.fetchEndereco(cep);

            if (address.getLogradouro() == null || address.getLogradouro().isEmpty()) {
                errorMessages.add("Insira um CEP válido!");
            } else {
                oldClient.setEndereco(AddressBuilder.buildAddress(address));
            }
        }
        return errorMessages;
    }
}
