package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class GroupEntityDto extends GroupDto{

    private Long id;

    public GroupEntityDto() {
    }

    public GroupEntityDto(String name, Integer schoolYear, Integer studentAmount, String program, String shift, Long id) {
        super(name, schoolYear, studentAmount, program, shift);
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
        GroupEntityDto that = (GroupEntityDto) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

    @Override
    public String toString() {
        return "GroupEntityDto{" +
                "id=" + id +
                '}';
    }
}
