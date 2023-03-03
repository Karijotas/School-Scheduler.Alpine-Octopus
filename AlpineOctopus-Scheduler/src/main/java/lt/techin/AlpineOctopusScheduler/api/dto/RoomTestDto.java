package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class RoomTestDto extends RoomDto {

    private Long id;

    public RoomTestDto() {
    }

    public RoomTestDto(Long id) {
        this.id = id;
    }

    public RoomTestDto(String name, String building, String description, Boolean deleted) {
        super(name, building, description, deleted);
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
        RoomTestDto that = (RoomTestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "RoomTestDto{" +
                "id=" + id +
                '}';
    }
}
