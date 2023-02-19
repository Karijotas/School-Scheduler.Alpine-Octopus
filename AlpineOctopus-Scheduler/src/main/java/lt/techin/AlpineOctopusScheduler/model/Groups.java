package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;
    @NotNull
    @Min(value = 2023, message = "School year should not be less than year the application was made")
    private Integer schoolYear;
    @NotNull
    private Integer studentAmount;
    @ManyToOne
    private Program program;
    @NotBlank
    private String shift;

    //private Shift shift;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;


    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
        createdBy = "API app";
        modifiedBy = "API app";
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
        modifiedBy = "API app";
    }

    public Groups(){}

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Integer getStudentAmount() {
        return studentAmount;
    }

    public void setStudentAmount(Integer studentAmount) {
        this.studentAmount = studentAmount;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groups groups = (Groups) o;
        return Objects.equals(getId(), groups.getId()) && Objects.equals(getName(), groups.getName()) && Objects.equals(getSchoolYear(), groups.getSchoolYear()) && Objects.equals(getStudentAmount(), groups.getStudentAmount()) && Objects.equals(getProgram(), groups.getProgram()) && Objects.equals(getShift(), groups.getShift()) && Objects.equals(getCreatedDate(), groups.getCreatedDate()) && Objects.equals(getModifiedDate(), groups.getModifiedDate()) && Objects.equals(getCreatedBy(), groups.getCreatedBy()) && Objects.equals(getModifiedBy(), groups.getModifiedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSchoolYear(), getStudentAmount(), getProgram(), getShift(), getCreatedDate(), getModifiedDate(), getCreatedBy(), getModifiedBy());
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", schoolYear=" + schoolYear +
                ", studentAmount=" + studentAmount +
                ", program='" + program + '\'' +
                ", shift='" + shift + '\'' +
                '}';
    }
}
