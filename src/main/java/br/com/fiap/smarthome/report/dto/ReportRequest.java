package br.com.fiap.smarthome.report.dto;

import br.com.fiap.smarthome.report.Report;
import br.com.fiap.smarthome.user.User;

import java.math.BigDecimal;

public record ReportRequest(User user) {

    public Report toModel(){
        return Report.builder()
                .user(user)
                .build();
    }

}
