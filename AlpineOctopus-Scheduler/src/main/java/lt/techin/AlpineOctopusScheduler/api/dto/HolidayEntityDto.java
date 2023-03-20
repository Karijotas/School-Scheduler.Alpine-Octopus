package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDate;
import java.util.Objects;

public class HolidayEntityDto extends HolidayDto {
    private Long id;

    public HolidayEntityDto() {
    }

    public HolidayEntityDto(Long id, String name, LocalDate startDate, LocalDate endDate) {
        super(name, startDate, endDate);
        this.id = id;
    }

    public HolidayEntityDto(String name, LocalDate startDate, LocalDate endDate, Long id) {

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
        HolidayEntityDto that = (HolidayEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "HolidayEntityDto{" +
                "id=" + id +
                '}';
    }
}
