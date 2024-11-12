package br.com.fiap.smarthome.energyConsumption;

import br.com.fiap.smarthome.report.Report;
import br.com.fiap.smarthome.report.ReportController;
import br.com.fiap.smarthome.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@Entity(name = "ENERGY_CONSUMPTION")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENERGY_CONSUMPTION_SEQ")
    @SequenceGenerator(name = "ENERGY_CONSUMPTION_SEQ", sequenceName = "ENERGY_CONSUMPTION_SEQ", allocationSize = 1)
    private Long consumptionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Builder.Default
    private LocalDateTime recordedAt = LocalDateTime.now();

    @Min(0)
    private BigDecimal totalEnergy;

    @Min(0)
    private BigDecimal cost;

    public EntityModel<EnergyConsumption> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(ReportController.class).readItem(consumptionId)).withSelfRel(),
                linkTo(methodOn(ReportController.class).delete(consumptionId)).withRel("delete"),
                linkTo(methodOn(ReportController.class).readAll(null)).withRel("contents")
        );
    }
}
