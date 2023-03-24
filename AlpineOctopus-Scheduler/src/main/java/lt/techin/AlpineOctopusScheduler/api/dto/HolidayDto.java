package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDate;
import java.util.Objects;

public class HolidayDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean reccuring;


    public HolidayDto() {
    }

    public HolidayDto(String name, LocalDate startDate, LocalDate endDate, Boolean reccuring) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reccuring = reccuring;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getReccuring() {
        return reccuring;
    }

    public void setReccuring(Boolean reccuring) {
        this.reccuring = reccuring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HolidayDto that = (HolidayDto) o;
        return Objects.equals(name, that.name) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(reccuring, that.reccuring);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, reccuring);
    }

    @Override
    public String toString() {
        return "HolidayDto{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reccuring=" + reccuring +
                '}';
    }
}
