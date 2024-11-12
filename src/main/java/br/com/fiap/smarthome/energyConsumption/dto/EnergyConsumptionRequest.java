package br.com.fiap.smarthome.energyConsumption.dto;

import br.com.fiap.smarthome.energyConsumption.EnergyConsumption;
import br.com.fiap.smarthome.user.User;

import java.math.BigDecimal;

public record EnergyConsumptionRequest(User user, BigDecimal totalEnergy) {

    public EnergyConsumption toModel(){
        return EnergyConsumption.builder()
                .user(user)
                .totalEnergy(totalEnergy)
                .build();
    }

}
