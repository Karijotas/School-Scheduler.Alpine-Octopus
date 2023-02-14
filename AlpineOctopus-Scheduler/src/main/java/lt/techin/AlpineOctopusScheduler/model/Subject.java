package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String description;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private Set<ProgramSubjectHours> subjectHour;

    public Subject() {

    }

    public Subject(Long id, String name, String description, Set<ProgramSubjectHours> subjectHour) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subjectHour = subjectHour;
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

    public Set<ProgramSubjectHours> getSubjectHour() {
        return subjectHour;
    }

    public void setSubjectHour(Set<ProgramSubjectHours> subjectHour) {
        this.subjectHour = subjectHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name) && Objects.equals(description, subject.description) && Objects.equals(subjectHour, subject.subjectHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, subjectHour);
    }
}
