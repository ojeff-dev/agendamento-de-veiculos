package com.jeffersonsantos.agendamento_de_veiculos.controllers;

import com.jeffersonsantos.agendamento_de_veiculos.controllers.dto.VeiculoDTO;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Veiculo;
import com.jeffersonsantos.agendamento_de_veiculos.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veiculos")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // retorna todos os veículos, juntamente com informações do cliente
    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> getAllVehicles() {
        List<Veiculo> vehicles = vehicleService.getAllVehicles();
        List<VeiculoDTO> vehicleDTOs = vehicles.stream()
                .map(VeiculoDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(vehicleDTOs);
    }

    // retorna o veículo de acordo com o id
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> getVehicleById(@PathVariable Long id) {
        Veiculo vehicle = vehicleService.getVehicleById(id);

        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(new VeiculoDTO(vehicle));
    }
}
