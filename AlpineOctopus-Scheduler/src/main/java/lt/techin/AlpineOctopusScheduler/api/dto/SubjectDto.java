package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.AlpineOctopusScheduler.model.Module;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class SubjectDto {

    private String name;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Set<Module> subjectModules;

    public SubjectDto() {
    }

    public SubjectDto(String name, String description,LocalDateTime createdDate, LocalDateTime modifiedDate,Set<Module> subjectModules ) {
        this.name = name;
        this.description = description;
        this.createdDate=createdDate;
        this.modifiedDate = modifiedDate;
        this.subjectModules = subjectModules;

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
    @JsonIgnore
    public Set<Module> getSubjectModules() {
        return subjectModules;
    }

    public void setSubjectModules(Set<Module> subjectModules) {
        this.subjectModules = subjectModules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDto that = (SubjectDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate) && Objects.equals(subjectModules, that.subjectModules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createdDate, modifiedDate, subjectModules);
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", subjectModules=" + subjectModules +
                '}';
    }
}
