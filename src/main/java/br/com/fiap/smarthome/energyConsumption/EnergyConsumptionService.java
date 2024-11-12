package br.com.fiap.smarthome.energyConsumption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnergyConsumptionService {

    @Autowired
    private EnergyConsumptionRepository repository;

    public EnergyConsumption create(EnergyConsumption energyConsumption) {
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

}
