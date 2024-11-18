package br.com.fiap.smarthome.userSettings;

import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserSettingsRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserSettingsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserSettings saveUserSettings(UserSettings userSettings) {
        String sql = "{call PKG_USER_SETTINGS.insert_user_settings(?, ?, ?)}";

        jdbcTemplate.update(sql,
                userSettings.getUser().getUserId(),
                userSettings.getCostLimit(),
                userSettings.getEmailAlert() ? 1 : 0
        );
        return userSettings;
    }
}
