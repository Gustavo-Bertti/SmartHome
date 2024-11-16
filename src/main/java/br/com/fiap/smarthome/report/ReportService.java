package br.com.fiap.smarthome.report;

import br.com.fiap.smarthome.energyConsumption.EnergyConsumption;
import br.com.fiap.smarthome.energyConsumption.EnergyConsumptionService;
import br.com.fiap.smarthome.report.reportAI.ReportAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository repository;

    @Autowired
    private ReportRepositoryProc repositoryProc;

    @Autowired
    private EnergyConsumptionService energyConsumptionService;

    @Autowired
    private ReportAIService reportAIService;

    public Report create(Report report) {
        report.setDefaultDates();
        return repositoryProc.saveReport(getReportData(report));
    }

    public Page<Report> readAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Report> readItem(Long id) {
        return repository.findById(id);
    }

    public Report update(Long id, Report report) {
        verifyReportExist(id);
        report.setReportId(id);
        return repository.save(getReportData(report));
    }

    private Report getReportData(Report report) {
        String message;
        BigDecimal totalEnergy = new BigDecimal(0);
        BigDecimal totalCost = new BigDecimal(0);
        List<EnergyConsumption> energyConsumptionsList = energyConsumptionService.findByUserIdAndRecordedAtBetween(report.getUser().getUserId(), report.getStartDate().atStartOfDay(), report.getEndDate().atStartOfDay());

        for (EnergyConsumption energyConsumption : energyConsumptionsList) {
            totalEnergy = totalEnergy.add(energyConsumption.getTotalEnergy());
            totalCost = totalCost.add(energyConsumption.getCost());
        }
        message = "Gastos no mês " + report.getStartDate().format(DateTimeFormatter.ofPattern("MM/yyyy")) + "\nEnergia total: " + totalEnergy + "kWh\nCusto: R$ " + totalCost;
        report.setConsumptionDescription(reportAIService.sendChatMessage(message));

        report.setTotalEnergy(totalEnergy);
        report.setTotalCost(totalCost);

        return report;
    }

    public void delete(Long id) {
        verifyReportExist(id);
        repository.deleteById(id);
    }

    public Page<Report> getReportsByUserId(Pageable pageable, Long userId) {
        return repository.getReportsByUser_UserId(pageable, userId);
    }

    private void verifyReportExist(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Report not found"));
    }
}
