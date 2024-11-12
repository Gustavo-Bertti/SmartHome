package br.com.fiap.smarthome.report;
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

    @JsonFormat(pattern="dd-MM-yyyy")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Size(max = 255)
    private String consumptionDescription;
}
