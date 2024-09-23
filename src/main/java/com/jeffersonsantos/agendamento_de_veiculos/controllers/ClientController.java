package com.jeffersonsantos.agendamento_de_veiculos.controllers;

import com.jeffersonsantos.agendamento_de_veiculos.builder.AddressBuilder;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.*;
import com.jeffersonsantos.agendamento_de_veiculos.services.ClientService;
import com.jeffersonsantos.agendamento_de_veiculos.util.ClientValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    private final ClientService clientService;
    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientService clientService, ClientValidator clientValidator) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
    }

    /*
    * POST /client
    * */
    @Operation(summary = "Cria um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados do cliente"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado para o CEP fornecido")
    })
    @PostMapping()
    public ResponseEntity<?> createClient(
            @Parameter(description = "Cliente a ser criado", required = true)
            @RequestBody Cliente cliente) {
        // Garante que não exista cliente sem veículo
        if (cliente.getVeiculos() == null || cliente.getVeiculos().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("É necessário inserir pelo menos 1 veículo.");
        }

        // busca o endereço do cliente através do CEP.
        String cep = cliente.getEndereco();
        Endereco address = clientService.fetchEndereco(cep);

        // verifica se a API retorna um endereço existente.
        if (address.getLogradouro() == null || address.getLogradouro().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Insira um CEP válido!"));
        }

        // se existir, formata o endereço e adiciona ao cliente.
        cliente.setEndereco(AddressBuilder.buildAddress(address));

        // associa os veículos ao cliente
        List<String> errors = clientValidator.validateVehicles(cliente);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(String.join(", ", errors)));
        }

        Cliente newClient = clientService.insertClient(cliente);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newClient);
    }

    /*
    * GET /client
    * */
    @GetMapping()
    public ResponseEntity<List<Cliente>> getAllClient() {
        List<Cliente> allClient = this.clientService.getAllClient();

        return ResponseEntity.ok(allClient);
    }

    /*
    * GET /client/id
    * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        Optional<Cliente> client = clientService.getClientById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Cliente não encontrado."));
    }

    /*
     * PATCH /client/{id}
     * */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Cliente newClient) {
        // Verifica se o cliente existe no BD.
        ResponseEntity<?> resp = getClientById(id);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
            return resp;
        }

        Cliente oldClient = (Cliente) resp.getBody();

        // verifica se os atributos devem/podem ser alterados, se não puderem, retorna uma lista com erro(s).
        List<String> vehicleErrors = clientValidator.validateVehicles(newClient);
        List<String> attributeErrors = clientValidator.validateAndUpdate(oldClient, newClient);
        List<String> allErrors = new ArrayList<>();
        allErrors.addAll(attributeErrors);
        allErrors.addAll(vehicleErrors);

        if (!allErrors.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(String.join(", ", allErrors)));
        }

        // Atualiza o cliente no BD e retorna como resposta à requisição.
        Cliente updatedClient = clientService.updateClient(oldClient);
        return ResponseEntity.ok(updatedClient);
    }

    /*
    * DELETE /client/{id}
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        ResponseEntity<?> resp = getClientById(id);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
            return resp;
        }

        clientService.deleteClient(id);
        return ResponseEntity.ok()
                .body("Cliente removido com sucesso!");
    }
}
