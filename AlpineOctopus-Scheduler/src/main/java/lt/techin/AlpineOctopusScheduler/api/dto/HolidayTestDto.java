package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDate;
import java.util.Objects;

public class HolidayTestDto extends HolidayDto {
    private Long id;

    public HolidayTestDto() {
    }

    public HolidayTestDto(String name, LocalDate startDate, LocalDate endDate) {
        super(name, startDate, endDate);
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
        HolidayTestDto that = (HolidayTestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "HolidayTestDto{" +
                "id=" + id +
                '}';
    }
}
