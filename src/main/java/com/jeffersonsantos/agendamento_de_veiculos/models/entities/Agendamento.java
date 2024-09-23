package com.jeffersonsantos.agendamento_de_veiculos.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Schema(description = "Representa um agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do cliente", example = "1", required = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    private LocalDateTime dataAgendamento;
    @Schema(description = "Descrição do agendamento", example = "Uma descrição qualquer", required = true)
    private String descricaoServico;
    @Enumerated(EnumType.STRING)
    @Schema(description = "Status do agendamento", example = "PENDENTE, REALIZADO ou CANCELADO", required = true)
    private Status status;

    public Agendamento() {}

    public Agendamento(Long id, Cliente cliente, LocalDateTime dataAgendamento,
                       String descricaoServico, Status status) {
        this.id = id;
        this.cliente = cliente;
        this.dataAgendamento = dataAgendamento;
        this.descricaoServico = descricaoServico;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
