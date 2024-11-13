package br.com.fiap.smarthome.userSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
    UserSettings getUserSettingsByUser_UserId(Long userId);
}