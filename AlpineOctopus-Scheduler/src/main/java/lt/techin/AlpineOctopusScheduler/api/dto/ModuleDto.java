package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class ModuleDto {

    private String name;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Set<Subject> modulesSubjects;

    public ModuleDto() {
    }

    public ModuleDto(String name, String description, LocalDateTime createdDate, LocalDateTime modifiedDate, Set<Subject> modulesSubjects) {
        this.name = name;
        this.description = description;
        this.createdDate=createdDate;
        this.modifiedDate=modifiedDate;
        this.modulesSubjects = modulesSubjects;

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
    public Set<Subject> getModulesSubjects() {
        return modulesSubjects;
    }

    public void setModulesSubjects(Set<Subject> modulesSubjects) {
        this.modulesSubjects = modulesSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleDto moduleDto = (ModuleDto) o;
        return Objects.equals(name, moduleDto.name) && Objects.equals(description, moduleDto.description) && Objects.equals(createdDate, moduleDto.createdDate) && Objects.equals(modifiedDate, moduleDto.modifiedDate) && Objects.equals(modulesSubjects, moduleDto.modulesSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createdDate, modifiedDate, modulesSubjects);
    }

    @Override
    public String toString() {
        return "ModuleDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", modulesSubjects=" + modulesSubjects +
                '}';
    }
}

