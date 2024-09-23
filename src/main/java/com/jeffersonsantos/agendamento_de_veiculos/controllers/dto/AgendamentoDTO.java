package com.jeffersonsantos.agendamento_de_veiculos.controllers.dto;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Agendamento;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Status;

// Representa os dados de agendamento que podem ser criados/atualizados
public class AgendamentoDTO {
    private Status status;
    private String descricaoServico;

    public AgendamentoDTO() {}

    public AgendamentoDTO(Agendamento agendamento) {
        this.status = agendamento.getStatus();
        this.descricaoServico = agendamento.getDescricaoServico();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }
}
