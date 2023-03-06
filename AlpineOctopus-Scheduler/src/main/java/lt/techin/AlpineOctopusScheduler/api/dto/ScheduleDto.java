package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScheduleDto {


    private String name;
    private LocalDateTime startingDate;

    private LocalDateTime plannedTillDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;


    public ScheduleDto() {
    }

    public ScheduleDto(String name, LocalDateTime startingDate, LocalDateTime plannedTillDate, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.name = name;
        this.startingDate = startingDate;
        this.plannedTillDate = plannedTillDate;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDateTime getPlannedTillDate() {
        return plannedTillDate;
    }

    public void setPlannedTillDate(LocalDateTime plannedTillDate) {
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
        return Objects.equals(getName(), that.getName()) && Objects.equals(getStartingDate(), that.getStartingDate()) && Objects.equals(getPlannedTillDate(), that.getPlannedTillDate()) && Objects.equals(getCreatedDate(), that.getCreatedDate()) && Objects.equals(getModifiedDate(), that.getModifiedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStartingDate(), getPlannedTillDate(), getCreatedDate(), getModifiedDate());
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "name='" + name + '\'' +
                ", startingDate=" + startingDate +
                ", plannedTillDate=" + plannedTillDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
