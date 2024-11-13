package br.com.fiap.smarthome.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EmailConsumptionDto {
    private String to;
    private LocalDateTime recordedAt;
    private BigDecimal totalEnergy;
    private BigDecimal cost;
    private BigDecimal costLimit;
}
