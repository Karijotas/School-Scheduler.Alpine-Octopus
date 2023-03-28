package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Subject subject;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Room room;

    private Integer lessonHours;
    private boolean online;

    private Integer status;

    private String statusMessage;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinTable(
            name = "lesson_Teachers",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<Teacher> subjectTeachers;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

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

    public Lesson() {
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Teacher> getSubjectTeachers() {
        return subjectTeachers;
    }

    public void setSubjectTeachers(List<Teacher> subjectTeachers) {
        this.subjectTeachers = subjectTeachers;
    }

    public Integer getLessonHours() {
        return lessonHours;
    }

    public void setLessonHours(Integer lessonHours) {
        this.lessonHours = lessonHours;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return isOnline() == lesson.isOnline() && Objects.equals(getId(), lesson.getId()) && Objects.equals(getSubject(), lesson.getSubject()) && Objects.equals(getTeacher(), lesson.getTeacher()) && Objects.equals(getRoom(), lesson.getRoom()) && Objects.equals(getLessonHours(), lesson.getLessonHours()) && Objects.equals(getStatus(), lesson.getStatus()) && Objects.equals(getStatusMessage(), lesson.getStatusMessage()) && Objects.equals(getSubjectTeachers(), lesson.getSubjectTeachers()) && Objects.equals(getStartTime(), lesson.getStartTime()) && Objects.equals(getEndTime(), lesson.getEndTime()) && Objects.equals(getCreatedDate(), lesson.getCreatedDate()) && Objects.equals(getModifiedDate(), lesson.getModifiedDate()) && Objects.equals(getCreatedBy(), lesson.getCreatedBy()) && Objects.equals(getModifiedBy(), lesson.getModifiedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getTeacher(), getRoom(), getLessonHours(), isOnline(), getStatus(), getStatusMessage(), getSubjectTeachers(), getStartTime(), getEndTime(), getCreatedDate(), getModifiedDate(), getCreatedBy(), getModifiedBy());
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", room=" + room +
                ", lessonHours=" + lessonHours +
                ", online=" + online +
                ", status=" + status +
                ", statusMessage='" + statusMessage + '\'' +
                ", subjectTeachers=" + subjectTeachers +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
