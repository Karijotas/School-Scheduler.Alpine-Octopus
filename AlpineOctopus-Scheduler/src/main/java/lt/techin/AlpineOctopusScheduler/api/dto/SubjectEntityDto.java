package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;

import java.util.Objects;
import java.util.Set;

public class SubjectEntityDto extends SubjectDto{

    private Long id;

    public SubjectEntityDto(){

    }

    public SubjectEntityDto(Long id, String name, String description){
        super(name, description);
        this.id= id;
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
        SubjectEntityDto that = (SubjectEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "SubjectEntityDto{" +
                "id=" + id +
                '}';
    }
}
