package br.com.fiap.smarthome.report.dto;

import br.com.fiap.smarthome.report.Report;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReportResponse(LocalDate startDate, LocalDate endDate, BigDecimal totalEnergy, BigDecimal totalCost, String consumptionDescription) {

    public static ReportResponse from(Report report) {
        return new ReportResponse(
                report.getStartDate(),
                report.getEndDate(),
                report.getTotalEnergy(),
                report.getTotalCost(),
                report.getConsumptionDescription());
    }

}
