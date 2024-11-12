package br.com.fiap.smarthome.energyConsumption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EnergyConsumptionService {

    private final BigDecimal kwhValue = new BigDecimal("0.85");

    @Autowired
    private EnergyConsumptionRepository repository;

    public EnergyConsumption create(EnergyConsumption energyConsumption) {
        energyConsumption.setCost(energyConsumption.getTotalEnergy().multiply(kwhValue));
        return repository.save(energyConsumption);
    }

    public List<EnergyConsumption> readAll() {
        return repository.findAll();
    }

    public Optional<EnergyConsumption> readItem(Long id) {
        return repository.findById(id);
    }

    public List<EnergyConsumption> getEnergyConsumptionsByUserId(Long userId) {
        return repository.getEnergyConsumptionsByUser_UserId(userId);
    }

    public EnergyConsumption getLastEnergyConsumptionByUserId(Long userId) {
        return repository.findTopByUser_UserIdOrderByRecordedAtDesc(userId);
    }
}
