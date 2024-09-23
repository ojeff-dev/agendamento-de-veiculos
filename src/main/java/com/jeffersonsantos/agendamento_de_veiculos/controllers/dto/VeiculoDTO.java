package com.jeffersonsantos.agendamento_de_veiculos.controllers.dto;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Veiculo;

// Representa como os dados serão retornados quando for feita uma requisição GET na rota de veículos.
public class VeiculoDTO {
    private Long id;
    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private String clienteNome;
    private String clienteCpf;

    public VeiculoDTO(Veiculo veiculo) {
        this.id = veiculo.getId();
        this.placa = veiculo.getPlaca();
        this.modelo = veiculo.getModelo();
        this.marca = veiculo.getMarca();
        this.ano = veiculo.getAno();
        this.clienteNome = veiculo.getCliente().getNome();
        this.clienteCpf = veiculo.getCliente().getCpf();
    }
}
