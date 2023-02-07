package lt.techin.AlpineOctopusScheduler.model;

import org.springframework.data.auditing.CurrentDateTimeProvider;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Group {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    @Min(value = 2023, message = "School year should not be less than year the application was made")
    private Integer schoolYear;
    private Integer studentAmount;
    private String program;
    private String shift;

}
