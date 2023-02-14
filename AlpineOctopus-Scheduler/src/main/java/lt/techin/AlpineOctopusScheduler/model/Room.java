package lt.techin.AlpineOctopusScheduler.model;

import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 5, max = 40)
    private String name;
    @NotBlank
    @Size(min = 5, max = 40)
    private String building;
    @Size(max = 100)
    private String description;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Room() {
    }

    public Room(Long id, String name, String building, String description,LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.description = description;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(building, room.building) && Objects.equals(description, room.description) && Objects.equals(modifiedDate, room.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, building, description, modifiedDate);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", building='" + building + '\'' +
                ", description='" + description + '\'' +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
