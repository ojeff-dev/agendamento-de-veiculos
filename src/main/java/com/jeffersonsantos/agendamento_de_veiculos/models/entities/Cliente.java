package com.jeffersonsantos.agendamento_de_veiculos.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Schema(description = "Representa um cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do cliente", example = "1", required = true)
    private Long id;
    @Schema(description = "Nome do cliente", example = "João Silva", required = true)
    private String nome;
    @Schema(description = "CPF do cliente", example = "123.456.789-00", required = true)
    private String cpf;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de veículos do cliente", required = true)
    private List<Veiculo> veiculos = new ArrayList<>();
    @Schema(description = "Endereço do cliente (CEP)", example = "25515-280", required = true)
    private String endereco;

    public Cliente() {}

    public Cliente(Long id, String nome, String endereco, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
