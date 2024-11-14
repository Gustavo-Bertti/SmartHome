package br.com.fiap.smarthome.energyConsumption;

import br.com.fiap.smarthome.email.EmailService;
import br.com.fiap.smarthome.email.dto.EmailConsumptionDto;
import br.com.fiap.smarthome.userSettings.UserSettings;
import br.com.fiap.smarthome.userSettings.UserSettingsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnergyConsumptionService {

    private final BigDecimal kwhValue = new BigDecimal("0.85");

    @Autowired
    private EnergyConsumptionRepository repository;
    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private EmailService emailService;


    public EnergyConsumption create(EnergyConsumption energyConsumption) {
        energyConsumption.setCost(energyConsumption.getTotalEnergy().multiply(kwhValue));

        UserSettings userSettings = userSettingsService.getUserSettingsByUserId(energyConsumption.getUser().getUserId());

        if (userSettings != null) {
            if (userSettings.getEmailAlert()){
                EmailConsumptionDto emailConsumptionDto = new EmailConsumptionDto(energyConsumption.getUser().getEmail(),
                        energyConsumption.getRecordedAt(),
                        energyConsumption.getTotalEnergy(),
                        energyConsumption.getCost(),
                        userSettings.getCostLimit()
                );

                emailService.sendConsumptionNotify(emailConsumptionDto);
            }
        }

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

    public List<EnergyConsumption> findByUserIdAndRecordedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByUserIdAndRecordedAtBetween(userId, startDate, endDate);
    }

    public List<EnergyConsumption> getEnergyConsumptionByMonth(@RequestParam String date, @RequestParam Long userId) {
        return repository.getEnergyConsumptionByMonthAndUserId(date, userId);
    }

}
