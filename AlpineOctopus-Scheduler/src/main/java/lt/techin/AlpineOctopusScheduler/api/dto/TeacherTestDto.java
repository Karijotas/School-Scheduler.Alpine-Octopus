package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Shift;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;
import java.util.Set;

public class TeacherTestDto extends TeacherDto {
    private Long id;

    public TeacherTestDto() {
    }

    public TeacherTestDto(Long id) {
        this.id = id;
    }

    public TeacherTestDto(String name, String loginEmail, String contactEmail, double workHoursPerWeek, String phone, Set<Shift> teacherShifts, Set<Subject> teacherSubjects, Boolean deleted, Long id) {
        super(name, loginEmail, contactEmail, workHoursPerWeek, phone, teacherShifts, teacherSubjects, deleted);
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
        TeacherTestDto that = (TeacherTestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "TeacherTestDto{" +
                "id=" + id +
                '}';
    }
}
