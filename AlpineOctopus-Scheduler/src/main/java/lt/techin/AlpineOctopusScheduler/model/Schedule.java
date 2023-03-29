package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startingDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate plannedTillDate;

    private Integer status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinTable(
            name = "schedule_subjects",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<Lesson> subjects = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinTable(
            name = "schedule_lessons",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "group_id")
    private Groups group;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "shift_id")
    private Shift shift;

    private Long groupIdValue;
    private String shiftName;


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

    public Schedule() {
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Long getGroupIdValue() {
        return groupIdValue;
    }

    public void setGroupIdValue(Long groupIdValue) {
        this.groupIdValue = groupIdValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Lesson> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Lesson> subjects) {
        this.subjects = subjects;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getPlannedTillDate() {
        return plannedTillDate;
    }

    public void setPlannedTillDate(LocalDate plannedTillDate) {
        this.plannedTillDate = plannedTillDate;
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

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void scheduleLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }
    public void updateLesson(Lesson lesson){
        this.lessons.remove(lesson.getId());
        this.lessons.add(lesson);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(name, schedule.name) && Objects.equals(startingDate, schedule.startingDate) && Objects.equals(plannedTillDate, schedule.plannedTillDate) && Objects.equals(status, schedule.status) && Objects.equals(subjects, schedule.subjects) && Objects.equals(lessons, schedule.lessons) && Objects.equals(group, schedule.group) && Objects.equals(shift, schedule.shift) && Objects.equals(groupIdValue, schedule.groupIdValue) && Objects.equals(shiftName, schedule.shiftName) && Objects.equals(createdDate, schedule.createdDate) && Objects.equals(modifiedDate, schedule.modifiedDate) && Objects.equals(createdBy, schedule.createdBy) && Objects.equals(modifiedBy, schedule.modifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startingDate, plannedTillDate, status, subjects, lessons, group, shift, groupIdValue, shiftName, createdDate, modifiedDate, createdBy, modifiedBy);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startingDate=" + startingDate +
                ", plannedTillDate=" + plannedTillDate +
                ", status=" + status +
                ", subjects=" + subjects +
                ", lessons=" + lessons +
                ", group=" + group +
                ", shift=" + shift +
                ", groupIdValue=" + groupIdValue +
                ", shiftName='" + shiftName + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}