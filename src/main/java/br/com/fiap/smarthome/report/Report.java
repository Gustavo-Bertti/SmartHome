package br.com.fiap.smarthome.report;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.fiap.smarthome.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORT_SEQ")
    @SequenceGenerator(name = "REPORT_SEQ", sequenceName = "REPORT_SEQ", allocationSize = 1)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate endDate;

    @Min(0)
    private BigDecimal totalEnergy;

    @Min(0)
    private BigDecimal totalCost;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Size(max = 500)
    @Builder.Default
    private String consumptionDescription = "teste";

    public void setDefaultDates() {
        LocalDate currentDate = LocalDate.now();
        this.startDate = currentDate.withDayOfMonth(1);

        currentDate = LocalDate.now();
        this.endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
    }

    public EntityModel<Report> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(ReportController.class).readItem(reportId)).withSelfRel(),
                linkTo(methodOn(ReportController.class).delete(reportId)).withRel("delete"),
                linkTo(methodOn(ReportController.class).readAll(null)).withRel("contents")
        );
    }
}
