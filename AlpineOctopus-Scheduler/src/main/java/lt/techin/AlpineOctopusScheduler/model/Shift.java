package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "SHIFT")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @Min(value = 1)
    @Max(value = 14)
    private Integer starts;
    @Min(value = 1)
    @Max(value = 14)
    private Integer ends;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;
    private Boolean deleted = Boolean.FALSE;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public Shift() {
    }

    public Shift(Long id, String name, Integer starts, Integer ends, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.starts = starts;
        this.ends = ends;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleted = deleted;
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

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public Integer getEnds() {
        return ends;
    }

    public void setEnds(Integer ends) {
        this.ends = ends;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shift shift = (Shift) o;
        return Objects.equals(id, shift.id) && Objects.equals(name, shift.name) && Objects.equals(starts, shift.starts) && Objects.equals(ends, shift.ends) && Objects.equals(createdDate, shift.createdDate) && Objects.equals(modifiedDate, shift.modifiedDate) && Objects.equals(deleted, shift.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, starts, ends, createdDate, modifiedDate, deleted);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", starts=" + starts +
                ", ends=" + ends +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", deleted=" + deleted +
                '}';
    }
}
