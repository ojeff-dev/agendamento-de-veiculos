package com.jeffersonsantos.agendamento_de_veiculos.builder;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Endereco;

// Essa classe serve para formatar o endereço que a API retorna
public class AddressBuilder {
    public static String buildAddress(Endereco address) {
        StringBuilder addressBuilder = new StringBuilder();

        if (address.getLogradouro() != null) {
            addressBuilder.append(address.getLogradouro());
        }
        // só adiciona complemento quando existir.
        if (address.getComplemento() != null && !address.getComplemento().isEmpty()) {
            addressBuilder.append(", ").append(address.getComplemento());
        }
        if (address.getBairro() != null) {
            addressBuilder.append(", ").append(address.getBairro());
        }
        if (address.getLocalidade() != null) {
            addressBuilder.append(", ").append(address.getLocalidade());
        }
        if (address.getUf() != null) {
            addressBuilder.append(", ").append(address.getUf());
        }
        if (address.getCep() != null) {
            addressBuilder.append(" - ").append(address.getCep());
        }

        return addressBuilder.toString();
    }
}
