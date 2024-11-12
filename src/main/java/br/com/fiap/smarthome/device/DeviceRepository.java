package br.com.fiap.smarthome.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> getDevicesByUser_UserId(Long userId);
}
