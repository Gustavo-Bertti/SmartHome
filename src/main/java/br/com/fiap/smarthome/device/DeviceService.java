package br.com.fiap.smarthome.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository repository;

    public Device create(Device device) {
        return repository.saveDevice(device);
    }

}
