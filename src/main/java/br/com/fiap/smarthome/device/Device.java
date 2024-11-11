package br.com.fiap.smarthome.device;

import br.com.fiap.smarthome.device.validation.Type;
import br.com.fiap.smarthome.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEVICES_SEQ")
    @SequenceGenerator(name = "DEVICES_SEQ", sequenceName = "DEVICES_SEQ", allocationSize = 1)
    private Long deviceId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Size(min = 1, max = 100)
    private String deviceName;

    @Size(max = 255)
    private String description;

    @Type
    private String usagePeriod;
}
