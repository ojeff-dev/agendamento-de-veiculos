package com.jeffersonsantos.agendamento_de_veiculos.models.repositories;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
