package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SUBJECT")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Negali buti tuscias")
    @Size(min = 1, max = 40)
    private String name;
    @Size(max = 500)
    private String description;

    private Boolean deleted = Boolean.FALSE;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "modules_subjects",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private Set<Module> subjectModules = new HashSet<>();
    ;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "rooms_subjects",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Room> subjectRooms = new HashSet<>();
    ;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> subjectTeachers = new HashSet<>();
    ;

    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private Set<ProgramSubjectHours> subjectHours;


    public Subject() {
        subjectHours = new HashSet<>();
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public Set<Module> getSubjectModules() {
        return subjectModules;
    }

    public void setSubjectModules(Set<Module> subjectModules) {
        this.subjectModules = subjectModules;
    }

    public Set<Room> getSubjectRooms() {
        return subjectRooms;
    }

    public void setSubjectRooms(Set<Room> subjectRooms) {
        this.subjectRooms = subjectRooms;
    }

    public Set<Teacher> getSubjectTeachers() {
        return subjectTeachers;
    }

    public void setSubjectTeachers(Set<Teacher> subjectTeachers) {
        this.subjectTeachers = subjectTeachers;
    }

    public Set<ProgramSubjectHours> getSubjectHours() {
        return subjectHours;
    }

    public void setSubjectHours(Set<ProgramSubjectHours> subjectHours) {
        this.subjectHours = subjectHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name) && Objects.equals(description, subject.description) && Objects.equals(deleted, subject.deleted) && Objects.equals(createdDate, subject.createdDate) && Objects.equals(modifiedDate, subject.modifiedDate) && Objects.equals(subjectModules, subject.subjectModules) && Objects.equals(subjectRooms, subject.subjectRooms) && Objects.equals(subjectTeachers, subject.subjectTeachers) && Objects.equals(subjectHours, subject.subjectHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, deleted, createdDate, modifiedDate, subjectModules, subjectRooms, subjectTeachers, subjectHours);
    }
}






