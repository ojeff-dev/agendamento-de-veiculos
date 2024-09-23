package com.jeffersonsantos.agendamento_de_veiculos.models.repositories;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Veiculo, Long> {
}
