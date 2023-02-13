package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "name")
    @Size(min = 5, max = 40)
    private String name;

    @NotBlank
    @Size(min = 5, max = 100)
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private Set<ProgramSubjectHours> subjectHours;

    public Program() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProgramSubjectHours> getSubjectHours() {
        return subjectHours;
    }

    public void setSubjectHours(Set<ProgramSubjectHours> subjectHours) {
        this.subjectHours = subjectHours;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(id, program.id) && Objects.equals(name, program.name) && Objects.equals(description, program.description) && Objects.equals(createdDate, program.createdDate) && Objects.equals(modifiedDate, program.modifiedDate) && Objects.equals(subjectHours, program.subjectHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdDate, modifiedDate, subjectHours);
    }
}
