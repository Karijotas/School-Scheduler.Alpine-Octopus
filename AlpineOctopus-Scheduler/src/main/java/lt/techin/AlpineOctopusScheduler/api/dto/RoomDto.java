package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class RoomDto {
    private String name;
    private String building;
    private String description;

    private Boolean deleted;

    public RoomDto() {
    }

    public RoomDto(String name, String building, String description, Boolean deleted) {
        this.name = name;
        this.building = building;
        this.description = description;
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(name, roomDto.name) && Objects.equals(building, roomDto.building) && Objects.equals(description, roomDto.description) && Objects.equals(deleted, roomDto.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building, description, deleted);
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "name='" + name + '\'' +
                ", building='" + building + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}