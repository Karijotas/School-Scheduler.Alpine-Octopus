package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ProgramEntityDto extends ProgramDto{

    private Long id;

    public ProgramEntityDto() {
    }

    public ProgramEntityDto(Long id, String name, String description, Set<Subject> subjects, LocalDateTime createdDate, LocalDateTime modifiedDate ) {
        super(name, description, subjects, createdDate, modifiedDate);
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
        ProgramEntityDto that = (ProgramEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "ProgramEntityDto{" +
                "id=" + id +
                '}';
    }
}
