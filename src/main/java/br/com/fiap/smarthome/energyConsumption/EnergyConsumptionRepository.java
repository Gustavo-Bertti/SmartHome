package br.com.fiap.smarthome.energyConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Long> {
    List<EnergyConsumption> getEnergyConsumptionsByUser_UserId(Long userId);

    EnergyConsumption findTopByUser_UserIdOrderByRecordedAtDesc(Long userId);
}
