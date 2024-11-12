package br.com.fiap.smarthome.userSettings;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
@RequestMapping("userSettings")
@CacheConfig(cacheNames = "userSettings")
@Tag(name = "UserSettings", description = "UserSettings-related endpoint")
public class UserSettingsController {

    @Autowired
    private UserSettingsService service;

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    public UserSettings create(@RequestBody @Valid UserSettings userSettings) {
        return service.create(userSettings);
    }

    @GetMapping
    @Cacheable
    public List<UserSettings> readAll() {
        return service.readAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserSettings> readItem(@PathVariable Long id) {
        return service.readItem(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    @CacheEvict(allEntries = true)
    public UserSettings update(@PathVariable Long id, @RequestBody UserSettings userSettings) {
        return service.update(id, userSettings);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("settings/{userId}")
    public List<UserSettings> getUserSettingsByUserId(@PathVariable Long userId) {
        return service.getUserSettingsByUserId(userId);
    }
}
