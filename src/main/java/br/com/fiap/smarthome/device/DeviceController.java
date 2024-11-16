package br.com.fiap.smarthome.device;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("device")
@CacheConfig(cacheNames = "devices")
@Tag(name = "Devices", description = "Device-related endpoint")
public class DeviceController {

    @Autowired
    private DeviceService service;

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    public Device create(@RequestBody @Valid Device device) {
        return service.create(device);
    }

}
