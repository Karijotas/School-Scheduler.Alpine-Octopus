package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lt.techin.AlpineOctopusScheduler.model.Lesson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class ScheduleDto {


    private String name;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startingDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate plannedTillDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    private Set<Lesson> subjects;

    private String groupName;
    private String shiftName;

    private Long groupId;

    private Long shiftId;


    public ScheduleDto() {
    }

    public ScheduleDto(String name, Integer status, LocalDate startingDate, LocalDate plannedTillDate, LocalDateTime createdDate, LocalDateTime modifiedDate, Set<Lesson> subjects, String groupName, String shiftName, Long groupId, Long shiftId) {
        this.name = name;
        this.status = status;
        this.startingDate = startingDate;
        this.plannedTillDate = plannedTillDate;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.subjects = subjects;
        this.groupName = groupName;
        this.shiftName = shiftName;
        this.groupId = groupId;
        this.shiftId = shiftId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDto that = (ScheduleDto) o;
        return Objects.equals(name, that.name) && Objects.equals(status, that.status) && Objects.equals(startingDate, that.startingDate) && Objects.equals(plannedTillDate, that.plannedTillDate) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate) && Objects.equals(subjects, that.subjects) && Objects.equals(groupName, that.groupName) && Objects.equals(shiftName, that.shiftName) && Objects.equals(groupId, that.groupId) && Objects.equals(shiftId, that.shiftId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status, startingDate, plannedTillDate, createdDate, modifiedDate, subjects, groupName, shiftName, groupId, shiftId);
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", startingDate=" + startingDate +
                ", plannedTillDate=" + plannedTillDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", subjects=" + subjects +
                ", groupName='" + groupName + '\'' +
                ", shiftName='" + shiftName + '\'' +
                ", groupId=" + groupId +
                ", shiftId=" + shiftId +
                '}';
    }
}
