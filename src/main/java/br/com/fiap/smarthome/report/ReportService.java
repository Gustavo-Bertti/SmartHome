package br.com.fiap.smarthome.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository repository;

    public Report create(Report report) {
        return repository.save(report);
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
        return repository.save(report);
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
