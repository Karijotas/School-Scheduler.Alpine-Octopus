package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ShiftEntityDto extends ShiftDto {

    private Long id;

    public ShiftEntityDto() {
    }

    public ShiftEntityDto(String name, int starts, int ends, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean deleted, Long id) {
        super(name, starts, ends, createdDate, modifiedDate, deleted);
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
        ShiftEntityDto that = (ShiftEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "ShiftEntityDto{" +
                "id=" + id +
                '}';
    }
}
