package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ModuleEntityDto extends ModuleDto {

    private Long id;

    public ModuleEntityDto() {

    }

    public ModuleEntityDto(String name, String description, Long id, Boolean deleted) {
        super(name, description, deleted);
        this.id = id;
    }

    public ModuleEntityDto(String name, String description, LocalDateTime createdDate, LocalDateTime modifiedDate, Long id) {
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
        ModuleEntityDto that = (ModuleEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "ModuleEntityDto{" +
                "id=" + id +
                '}';
    }
}

