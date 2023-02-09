package lt.techin.AlpineOctopusScheduler.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Room {
    @Id
    @NotBlank
    private String name;
    @NotBlank
    private String building;
    private String description;

    public Room() {
    }

    public Room(String name, String building, String description) {
        this.name = name;
        this.building = building;
        this.description = description;
    }

    public String getClassName() {
        return name;
    }

    public void setClassName(String className) {
        this.name = className;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return name.equals(room.name) && building.equals(room.building) && Objects.equals(description, room.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building, description);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", building='" + building + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

