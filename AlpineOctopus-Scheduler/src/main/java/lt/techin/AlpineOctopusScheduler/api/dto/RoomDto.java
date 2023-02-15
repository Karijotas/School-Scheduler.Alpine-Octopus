package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;
import java.util.Set;

public class RoomDto {
    private String name;
    private String building;
    private String description;
    private Set<Subject> roomSubjects;

    public RoomDto() {
    }

    public RoomDto(String name, String building, String description, Set<Subject> roomSubjects) {
        this.name = name;
        this.building = building;
        this.description = description;
        this.roomSubjects = roomSubjects;
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
    @JsonIgnore
    public Set<Subject> getRoomSubjects() {
        return roomSubjects;
    }

    public void setRoomSubjects(Set<Subject> roomSubjects) {
        this.roomSubjects = roomSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(name, roomDto.name) && Objects.equals(building, roomDto.building) && Objects.equals(description, roomDto.description) && Objects.equals(roomSubjects, roomDto.roomSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building, description, roomSubjects);
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "name='" + name + '\'' +
                ", building='" + building + '\'' +
                ", description='" + description + '\'' +
                ", roomSubjects=" + roomSubjects +
                '}';
    }
}