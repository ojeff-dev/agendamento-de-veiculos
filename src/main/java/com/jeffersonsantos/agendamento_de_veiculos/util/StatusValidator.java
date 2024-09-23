package com.jeffersonsantos.agendamento_de_veiculos.util;

import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Status;

/**
 * Verifica se o status fornecido é válido.
 * retorna true se o status for válido, false caso contrário.
 */
public class StatusValidator {
    public static boolean isValidStatus(Status status) {
        if (status == null) {
            return false;
        }
        for (Status s : Status.values()) {
            if (s == status) {
                return true;
            }
        }
        return false;
    }
}
