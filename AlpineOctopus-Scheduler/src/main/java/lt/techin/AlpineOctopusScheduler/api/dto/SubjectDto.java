package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
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
    private Set<Room> subjectRooms;

    private Set<Teacher> subjectTeachers;

    public SubjectDto() {
    }

    public SubjectDto(String name, String description,LocalDateTime createdDate, LocalDateTime modifiedDate,Set<Module> subjectModules,Set<Room> subjectRooms, Set<Teacher> subjectTeachers ) {
        this.name = name;
        this.description = description;
        this.createdDate=createdDate;
        this.modifiedDate = modifiedDate;
        this.subjectModules = subjectModules;
        this.subjectRooms = subjectRooms;
        this.subjectTeachers = subjectTeachers;

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
    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDto that = (SubjectDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate) && Objects.equals(subjectModules, that.subjectModules) && Objects.equals(subjectRooms, that.subjectRooms) && Objects.equals(subjectTeachers, that.subjectTeachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createdDate, modifiedDate, subjectModules, subjectRooms, subjectTeachers);
    }

    @Override
    public String toString() {
        return "SubjectDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", subjectModules=" + subjectModules +
                ", subjectRooms=" + subjectRooms +
                ", subjectTeachers=" + subjectTeachers +
                '}';
    }
}
