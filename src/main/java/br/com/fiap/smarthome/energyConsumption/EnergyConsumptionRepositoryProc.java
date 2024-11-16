package br.com.fiap.smarthome.energyConsumption;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnergyConsumptionRepositoryProc {

    private final JdbcTemplate jdbcTemplate;

    public EnergyConsumptionRepositoryProc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public EnergyConsumption saveEnergyConsumption(EnergyConsumption energyConsumption) {
        String sql = "{call SAVE_ENERGY_CONSUMPTION(?, ?, ?, ?)}";

        jdbcTemplate.update(sql,
                energyConsumption.getUser().getUserId(),
                energyConsumption.getTotalEnergy(),
                energyConsumption.getCost(),
                energyConsumption.getRecordedAt()
        );
        return energyConsumption;
    }
}
