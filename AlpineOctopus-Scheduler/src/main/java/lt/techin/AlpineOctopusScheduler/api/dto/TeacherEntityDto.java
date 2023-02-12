package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class TeacherEntityDto extends TeacherDto{

    private Long id;

    public TeacherEntityDto () {

    }
    public TeacherEntityDto(String name, String surname, String contactEmail, String phone, String shift) {
        super(name, surname, contactEmail, phone, shift);
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
