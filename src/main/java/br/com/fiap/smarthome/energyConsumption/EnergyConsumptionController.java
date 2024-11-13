package br.com.fiap.smarthome.energyConsumption;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import br.com.fiap.smarthome.email.dto.EmailConsumptionDto;
import br.com.fiap.smarthome.energyConsumption.dto.EnergyConsumptionRequest;
import br.com.fiap.smarthome.energyConsumption.dto.EnergyConsumptionResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<EnergyConsumptionResponse> create(@RequestBody @Valid EnergyConsumptionRequest energyConsumptionRequest, UriComponentsBuilder uriBuilder) {
        EnergyConsumption energyConsumption = service.create(energyConsumptionRequest.toModel());

        var uri = uriBuilder
                .path("/energyConsumption/{id}")
                .buildAndExpand(energyConsumption.getConsumptionId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(EnergyConsumptionResponse.from(energyConsumption));
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

    @GetMapping("lastEnergyConsumption/{userId}")
    public EnergyConsumption getLastEnergyConsumptionByUserId(@PathVariable Long userId) {
        return service.getLastEnergyConsumptionByUserId(userId);
    }

    @GetMapping("consumptionByMonth")
    public List<EnergyConsumption> getEnergyConsumptionByMonth(@RequestParam String date, @RequestParam Long userId) {
        return service.getEnergyConsumptionByMonth(date, userId);
    }

}
