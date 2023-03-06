package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScheduleTestDto extends ScheduleDto {

    private Long id;

    public ScheduleTestDto() {
    }

    public ScheduleTestDto(Long id) {
        this.id = id;
    }

    public ScheduleTestDto(String name, LocalDateTime startingDate, LocalDateTime plannedTillDate, LocalDateTime createdDate, LocalDateTime modifiedDate, Long id) {
        super(name, startingDate, plannedTillDate, createdDate, modifiedDate);
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
        ScheduleTestDto that = (ScheduleTestDto) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

    @Override
    public String
    toString() {
        return "ScheduleTestDto{" +
                "id=" + id +
                '}';
    }
}
