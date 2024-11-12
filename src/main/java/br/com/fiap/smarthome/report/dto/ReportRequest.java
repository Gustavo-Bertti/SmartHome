package br.com.fiap.smarthome.report.dto;

import br.com.fiap.smarthome.report.Report;
import br.com.fiap.smarthome.user.User;

import java.math.BigDecimal;

public record ReportRequest(User user, BigDecimal totalEnergy, BigDecimal totalCost) {

    public Report toModel(){
        return Report.builder()
                .user(user)
                .totalEnergy(totalEnergy)
                .totalCost(totalCost)
                .build();
    }

}
