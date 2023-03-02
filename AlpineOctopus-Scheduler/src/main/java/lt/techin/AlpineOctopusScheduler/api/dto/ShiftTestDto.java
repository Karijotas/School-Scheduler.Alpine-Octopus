package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ShiftTestDto extends ShiftDto {

    private Long id;

    public ShiftTestDto() {
    }

    public ShiftTestDto(Long id) {
        this.id = id;
    }

    public ShiftTestDto(String name, int starts, int ends, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean deleted) {
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
        ShiftTestDto that = (ShiftTestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "ShiftTestDto{" +
                "id=" + id +
                '}';
    }
}
