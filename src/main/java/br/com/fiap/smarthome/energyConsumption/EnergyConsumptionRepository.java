package br.com.fiap.smarthome.energyConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Long> {
    List<EnergyConsumption> getEnergyConsumptionsByUser_UserId(Long userId);

    EnergyConsumption findTopByUser_UserIdOrderByRecordedAtDesc(Long userId);

    @Query("SELECT e FROM ENERGY_CONSUMPTION e JOIN e.user u WHERE u.userId = :userId AND e.recordedAt >= :startDate AND e.recordedAt <= :endDate")
    List<EnergyConsumption> findByUserIdAndRecordedAtBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
