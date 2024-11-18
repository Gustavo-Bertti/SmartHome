package br.com.fiap.smarthome.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> getReportsByUser_UserId(Pageable pageable, Long userId);

    @Query("SELECT r FROM Report r WHERE r.user.userId = :userId AND MONTH(r.createdAt) = MONTH(CURRENT_DATE) AND YEAR(r.createdAt) = YEAR(CURRENT_DATE)")
    Optional<Report> findCurrentMonthReportByUser_UserId(@Param("userId") Long userId);
}
