package br.com.fiap.smarthome.userSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@Service
public class UserSettingsService {

    @Autowired
    private UserSettingsRepository repository;

    public UserSettings create(UserSettings userSettings) {
        return repository.save(userSettings);
    }

    public UserSettings update(Long id, UserSettings userSettings) {
        verifyUserSettingsExist(id);
        userSettings.setSettingId(id);
        return repository.save(userSettings);
    }

    public List<UserSettings> getUserSettingsByUserId(Long userId) {
        return repository.getUserSettingsByUser_UserId(userId);
    }

    private void verifyUserSettingsExist(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "UserSettings not found"));
    }
}
