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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "name", unique = true)
    @Size(min = 5, max = 40)
    private String name;

    @NotBlank
    @Size(min = 5, max = 100)
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "program")
    private Set<Groups> groupsSet;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
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

    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private Set<ProgramSubjectHours> subjectHours;

    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private Set<Groups> groups;

    public Program() {
        subjectHours = new HashSet<>();
        groups = new HashSet<>();
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

    public Set<Groups> getGroupsSet() {
        return groupsSet;
    }

    public void setGroupsSet(Set<Groups> groupsSet) {
        this.groupsSet = groupsSet;
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

    public Set<ProgramSubjectHours> getSubjectHours() {
        return subjectHours;
    }

    public void setSubjectHours(Set<ProgramSubjectHours> subjectHours) {
        this.subjectHours = subjectHours;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(id, program.id) && Objects.equals(name, program.name) && Objects.equals(description, program.description) && Objects.equals(groupsSet, program.groupsSet) && Objects.equals(createdDate, program.createdDate) && Objects.equals(modifiedDate, program.modifiedDate) && Objects.equals(deleted, program.deleted) && Objects.equals(subjectHours, program.subjectHours) && Objects.equals(groups, program.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, groupsSet, createdDate, modifiedDate, deleted, subjectHours, groups);
    }
}
