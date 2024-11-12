package br.com.fiap.smarthome.energyConsumption.dto;

import br.com.fiap.smarthome.energyConsumption.EnergyConsumption;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnergyConsumptionResponse(BigDecimal totalEnergy, BigDecimal cost, LocalDateTime recordedAt) {

    public static EnergyConsumptionResponse from(EnergyConsumption energyConsumption) {
        return new EnergyConsumptionResponse(
                energyConsumption.getTotalEnergy(),
                energyConsumption.getCost(),
                energyConsumption.getRecordedAt());
    }

}
