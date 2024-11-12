package br.com.fiap.smarthome.userSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
    List<UserSettings> getUserSettingsByUser_UserId(Long userId);
}
