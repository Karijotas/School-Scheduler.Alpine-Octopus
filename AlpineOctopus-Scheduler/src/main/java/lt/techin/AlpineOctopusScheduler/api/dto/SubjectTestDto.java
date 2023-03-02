package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.model.Teacher;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class SubjectTestDto extends SubjectDto {

    private Long id;

    public SubjectTestDto() {
    }

    public SubjectTestDto(Long id) {
        this.id = id;
    }

    public SubjectTestDto(String name, String description, LocalDateTime createdDate, LocalDateTime modifiedDate, Set<Module> subjectModules, Set<Room> subjectRooms, Set<Teacher> subjectTeachers, Boolean deleted) {
        super(name, description, createdDate, modifiedDate, subjectModules, subjectRooms, subjectTeachers, deleted);
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
        SubjectTestDto that = (SubjectTestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "SubjectTestDto{" +
                "id=" + id +
                '}';
    }
}
