package br.com.fiap.smarthome.userSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.Optional;

@Service
public class UserSettingsService {

    @Autowired
    private UserSettingsRepository repository;

    public UserSettings create(UserSettings userSettings) {
        return repository.save(userSettings);
    }

    public List<UserSettings> readAll() {
        return repository.findAll();
    }

    public Optional<UserSettings> readItem(Long id) {
        return repository.findById(id);
    }

    public UserSettings update(Long id, UserSettings userSettings) {
        verifyUserSettingsExist(id);
        userSettings.setSettingId(id);
        return repository.save(userSettings);
    }

    public void delete(Long id) {
        verifyUserSettingsExist(id);
        repository.deleteById(id);
    }

    public List<UserSettings> getUserSettingsByUserId(Long userId) {
        return repository.getUserSettingsByUser_UserId(userId);
    }

    private void verifyUserSettingsExist(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "UserSettings not found"));
    }
}
