package br.com.fiap.smarthome.energyConsumption;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("energyConsumption")
@CacheConfig(cacheNames = "energyConsumptions")
@Tag(name = "EnergyConsumptions", description = "EnergyConsumption-related endpoint")
public class EnergyConsumptionController {

    @Autowired
    private EnergyConsumptionService service;

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    public EnergyConsumption create(@RequestBody @Valid EnergyConsumption energyConsumption) {
        return service.create(energyConsumption);
    }

    @GetMapping
    @Cacheable
    public List<EnergyConsumption> readAll() {
        return service.readAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<EnergyConsumption> readItem(@PathVariable Long id) {
        return service.readItem(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("userEnergyConsumption/{userId}")
    public List<EnergyConsumption> getEnergyConsumptionsByUserId(@PathVariable Long userId) {
        return service.getEnergyConsumptionsByUserId(userId);
    }
}
