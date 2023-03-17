package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class ModuleTestDto extends ModuleDto {

    private Long id;

    public ModuleTestDto() {
    }

    public ModuleTestDto(Long id) {
        this.id = id;
    }

    public ModuleTestDto(String name, String description, Boolean deleted) {
        super(name, description, deleted);
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
        ModuleTestDto that = (ModuleTestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "ModuleTestDto{" +
                "id=" + id +
                '}';
    }
}
