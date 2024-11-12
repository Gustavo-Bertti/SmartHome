package br.com.fiap.smarthome.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository repository;

    public Device create(Device device) {
        return repository.save(device);
    }

    public List<Device> readAll() {
        return repository.findAll();
    }

    public Optional<Device> readItem(Long id) {
        return repository.findById(id);
    }

    public Device update(Long id, Device device) {
        verifyDeviceExist(id);
        device.setDeviceId(id);
        return repository.save(device);
    }

    public void delete(Long id) {
        verifyDeviceExist(id);
        repository.deleteById(id);
    }

    public List<Device> getDevicesByUserId(Long userId) {
        return repository.getDevicesByUser_UserId(userId);
    }

    private void verifyDeviceExist(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Device not found"));
    }
}
