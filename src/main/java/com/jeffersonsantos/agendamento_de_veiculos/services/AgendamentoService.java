package com.jeffersonsantos.agendamento_de_veiculos.services;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Agendamento;
import com.jeffersonsantos.agendamento_de_veiculos.models.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    // criar um novo agendamento
    public Agendamento createAgendamento(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    // buscar todos os agendamentos
    public List<Agendamento> getAllAgendamentos() {
        return agendamentoRepository.findAll();
    }

    // buscar um agendamento por ID
    public Agendamento getAgendamentoById(Long id) {
        return agendamentoRepository.findById(id).orElse(null);
    }

    // atualizar um agendamento
    public Agendamento updateAgendamento(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    // remover um agendamento
    public void deleteAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
