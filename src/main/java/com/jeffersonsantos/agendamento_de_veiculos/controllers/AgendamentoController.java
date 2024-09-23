package com.jeffersonsantos.agendamento_de_veiculos.controllers;

import com.jeffersonsantos.agendamento_de_veiculos.controllers.dto.AgendamentoDTO;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Agendamento;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Cliente;
import com.jeffersonsantos.agendamento_de_veiculos.services.AgendamentoService;
import com.jeffersonsantos.agendamento_de_veiculos.services.ClientService;
import com.jeffersonsantos.agendamento_de_veiculos.util.StatusValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    private final AgendamentoService agendamentoService;
    private final ClientService clientService;

    @Autowired
    public AgendamentoController(AgendamentoService agendamentoService,
                                 ClientService clientService) {
        this.agendamentoService = agendamentoService;
        this.clientService = clientService;
    }

    /*
    * POST /agendamento/{id} (ID do cliente)
    * */
    @Operation(summary = "Cria um novo agendamento")
    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @PostMapping("/{id}")
    public ResponseEntity<?> createAgendamento(@PathVariable Long id,
                                                         @RequestBody AgendamentoDTO agendamentoDTO) {
        // verificando se o cliente existe.
        Optional<Cliente> clienteResp = clientService.getClientById(id);
        if (clienteResp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente não encontrado.");
        }

        // verifica se o status é válido.
        if (!StatusValidator.isValidStatus(agendamentoDTO.getStatus())) {
            return ResponseEntity.badRequest()
                    .body("Status inválido!");
        }

        // criando um novo objeto de agendamento.
        Agendamento newAgendamento = new Agendamento();
        newAgendamento.setCliente(clienteResp.get());
        newAgendamento.setDataAgendamento(LocalDateTime.now());
        newAgendamento.setDescricaoServico(agendamentoDTO.getDescricaoServico());
        newAgendamento.setStatus(agendamentoDTO.getStatus());

        // salva e retorna o agendamento.
        Agendamento createdAgendamento = agendamentoService.createAgendamento(newAgendamento);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdAgendamento);
    }

    /*
    * GET /agendamento
    * */
    @GetMapping()
    public ResponseEntity<List<Agendamento>> getAllAgendamentos() {
        List<Agendamento> agendamentos = agendamentoService.getAllAgendamentos();

        return ResponseEntity.ok(agendamentos);
    }

    /*
    * GET /agendamento/{id} (ID do agendamento)
    * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAgendamentoById(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.getAgendamentoById(id);

        if (agendamento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhum agendamento foi encontrado.");
        }
        return ResponseEntity.ok(agendamento);
    }

    /*
    * PATCH /agendamento/{id} (ID do agendamento)
    * */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAgendamento(@PathVariable Long id,
                                               @RequestBody AgendamentoDTO agendamentoDTO) {
        ResponseEntity<?> resp = getAgendamentoById(id);

        if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
            return resp;
        }

        Agendamento agendamento = (Agendamento) resp.getBody();

        // verifica se o status é válido
        if (!StatusValidator.isValidStatus(agendamentoDTO.getStatus())) {
            return ResponseEntity.badRequest()
                    .body("Status inválido!");
        }

        // Atualiza os campos
        assert agendamento != null;
        agendamento.setDescricaoServico(agendamentoDTO.getDescricaoServico());
        agendamento.setStatus(agendamentoDTO.getStatus());

        // salva o agendamento e retorna como resposta.
        Agendamento updatedAgendamento = agendamentoService
                .updateAgendamento(agendamento);

        return ResponseEntity.ok(updatedAgendamento);
    }
}
