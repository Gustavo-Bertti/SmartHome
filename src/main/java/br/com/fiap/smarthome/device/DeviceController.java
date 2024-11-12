package br.com.fiap.smarthome.device;

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
@RequestMapping("device")
@CacheConfig(cacheNames = "devices")
@Tag(name = "Devices", description = "Device-related com devices")
public class DeviceController {

    @Autowired
    private DeviceService service;

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    public Device create(@RequestBody @Valid Device device) {
        return service.create(device);
    }

    @GetMapping
    @Cacheable
    public List<Device> readAll() {
        return service.readAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Device> readItem(@PathVariable Long id) {
        return service.readItem(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    @CacheEvict(allEntries = true)
    public Device update(@PathVariable Long id, @RequestBody Device device) {
        return service.update(id, device);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("userDevice/{userId}")
    public List<Device> getDevicesByUserId(@PathVariable Long userId) {
        return service.getDevicesByUserId(userId);
    }
}
