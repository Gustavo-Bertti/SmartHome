package br.com.fiap.smarthome.energyConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Long> {
    @Query("SELECT e FROM ENERGY_CONSUMPTION e WHERE e.user.userId = :userId ORDER BY e.recordedAt DESC")
    List<EnergyConsumption> getEnergyConsumptionsByUser_UserId(Long userId);

    EnergyConsumption findTopByUser_UserIdOrderByRecordedAtDesc(Long userId);

    @Query("SELECT e FROM ENERGY_CONSUMPTION e JOIN e.user u WHERE u.userId = :userId AND e.recordedAt >= :startDate AND e.recordedAt <= :endDate")
    List<EnergyConsumption> findByUserIdAndRecordedAtBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM ENERGY_CONSUMPTION A " +
            "WHERE A.RECORDED_AT >= DATEFROMPARTS(CAST(SUBSTRING(:date, 4, 4) AS INT), CAST(SUBSTRING(:date, 1, 2) AS INT), 1) " +
            "AND A.RECORDED_AT < DATEADD(MONTH, 1, DATEFROMPARTS(CAST(SUBSTRING(:date, 4, 4) AS INT), CAST(SUBSTRING(:date, 1, 2) AS INT), 1)) " +
            "AND A.USER_ID = :userId ORDER BY A.RECORDED_AT DESC",
            nativeQuery = true)
    List<EnergyConsumption> getEnergyConsumptionByMonthAndUserId(String date, Long userId);

}
