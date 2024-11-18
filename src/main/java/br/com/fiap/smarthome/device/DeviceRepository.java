package br.com.fiap.smarthome.device;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceRepository {

    private final JdbcTemplate jdbcTemplate;

    public DeviceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Device saveDevice(Device device) {
        String sql = "{call PKG_DEVICE.INSERT_DEVICE(?, ?, ?, ?)}";

        jdbcTemplate.update(sql,
                device.getUser().getUserId(),
                device.getDeviceName(),
                device.getDescription(),
                device.getUsagePeriod()
        );
        return device;
    }
}