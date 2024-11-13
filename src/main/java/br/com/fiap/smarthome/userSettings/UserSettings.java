package br.com.fiap.smarthome.userSettings;

import br.com.fiap.smarthome.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SETTINGS_SEQ")
    @SequenceGenerator(name = "USER_SETTINGS_SEQ", sequenceName = "USER_SETTINGS_SEQ", allocationSize = 1)
    private Long settingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal costLimit;

    private Boolean emailAlert;
}
