package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Lesson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class ScheduleEntityDto extends ScheduleDto {
    private Long id;

    public ScheduleEntityDto() {
    }


    public ScheduleEntityDto(String name, Integer status, LocalDate startingDate, LocalDate plannedTillDate, LocalDateTime createdDate, LocalDateTime modifiedDate, Set<Lesson> lessons, String groupName, String shiftName, Long groupId, Long shiftId, Long id) {
        super(name, status, startingDate, plannedTillDate, createdDate, modifiedDate, lessons, groupName, shiftName, groupId, shiftId);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ScheduleEntityDto that = (ScheduleEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "ScheduleEntityDto{" +
                "id=" + id +
                '}';
    }
}
