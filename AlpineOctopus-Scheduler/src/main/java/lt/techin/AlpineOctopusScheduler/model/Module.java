package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Negali buti tuscias")
    @Size(min = 5, max = 40)
    private String name;
    @Size(min = 5, max = 100)
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    @ManyToMany (mappedBy = "subjectModules")
    @JsonIgnore
    private Set<Subject> modulesSubjects;

    public Module() {

    }
    public Module(Long id, String name, String description, LocalDateTime createdDate, LocalDateTime modifiedDate,Set<Subject> modulesSubjects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate =createdDate;
        this.modifiedDate=modifiedDate;
        this.modulesSubjects = modulesSubjects;
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
        Module module = (Module) o;
        return Objects.equals(id, module.id) && Objects.equals(name, module.name) && Objects.equals(description, module.description) && Objects.equals(createdDate, module.createdDate) && Objects.equals(modifiedDate, module.modifiedDate) && Objects.equals(modulesSubjects, module.modulesSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdDate, modifiedDate, modulesSubjects);
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", modulesSubjects=" + modulesSubjects +
                '}';
    }
}
