package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class ModuleDto {

    private String name;

    private String description;

    public ModuleDto() {
    }

    public ModuleDto(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleDto moduleDto = (ModuleDto) o;
        return Objects.equals(name, moduleDto.name) && Objects.equals(description, moduleDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "ModuleDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

