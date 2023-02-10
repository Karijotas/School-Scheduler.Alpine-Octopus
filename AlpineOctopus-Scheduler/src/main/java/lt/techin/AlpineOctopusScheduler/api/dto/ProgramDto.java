package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ProgramDto {
    private String name;

    private String description;

    private Set<Subject> subjects;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;


    public ProgramDto() {
    }

    public ProgramDto(String name, String description, Set<Subject> subjects, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.name = name;
        this.description = description;
        this.subjects = subjects;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramDto that = (ProgramDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(subjects, that.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, subjects);
    }

    @Override
    public String toString() {
        return "ProgramMapper{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
