package com.jeffersonsantos.agendamento_de_veiculos.services;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Veiculo;
import com.jeffersonsantos.agendamento_de_veiculos.models.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // retorna todos os veículos
    public List<Veiculo> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // retornar veículo por ID
    public Veiculo getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElse(null);
    }
}
