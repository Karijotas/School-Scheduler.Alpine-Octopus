package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;
import java.util.Set;

public class TeacherEntityDto extends TeacherDto{

    private Long id;

    public TeacherEntityDto () {

    }
    public TeacherEntityDto(String name, String surname, String contactEmail, String phone, String shift, Set<Subject> teachersSubjects,Long id) {
        super(name, surname, contactEmail, phone, shift, teachersSubjects);
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
        TeacherEntityDto that = (TeacherEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "TeacherEntityDto{" +
                "id=" + id +
                '}';
    }
}
